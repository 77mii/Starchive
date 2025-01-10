import { Users } from "@prisma/client"
import { prismaClient } from "../application/database"
import { logger } from "../application/logging"
import { ResponseError } from "../error/response-error"
import {
    toUserResponse,
    RegisterUserRequest,
    UserResponse,
    LoginUserRequest,
} from "../models/user-model"
import { UserValidation } from "../validations/user-validation"
import { Validation } from "../validations/validation"
import bcrypt from "bcrypt"
import { v4 as uuid } from "uuid"

export class UserService {
    static async register(request: RegisterUserRequest): Promise<UserResponse> {
        // validate request
        const registerRequest = Validation.validate(
            UserValidation.REGISTER,
            request
        )

        const email = await prismaClient.users.findFirst({
            where: {
                username: registerRequest.username,
            },
        })

        if (email) {
            throw new ResponseError(400, "Username already exists!")
        }

        // encrypt password
        registerRequest.password = await bcrypt.hash(
            registerRequest.password,
            10
        )

        // add user to db
        const user = await prismaClient.users.create({
            data: {
                username: registerRequest.username,
                password: registerRequest.password,
                token: uuid(),
            },
        })

        // convert user to UserResponse and return it
        return toUserResponse(user)
    }

    static async login(request: LoginUserRequest): Promise<UserResponse> {
        const loginRequest = Validation.validate(UserValidation.LOGIN, request)

        let user = await prismaClient.users.findFirst({
            where: {
                username: loginRequest.username,
            },
        })

        if (!user) {
            throw new ResponseError(400, "Invalid username or password!")
        }

        const passwordIsValid = await bcrypt.compare(
            loginRequest.password,
            user.password
        )

        if (!passwordIsValid) {
            throw new ResponseError(400, "Invalid email or password!")
        }

        user = await prismaClient.users.update({
            where: {
                user_id: user.user_id,
            },
            data: {
                token: uuid(),
            },
        })

        const response = toUserResponse(user)

        return response
    }

    static async logout(user: Users): Promise<string> {
        const result = await prismaClient.users.update({
            where: {
                user_id: user.user_id,
            },
            data: {
                token: null,
            },
        })

        return "Logout Successful!"
    }
    //the below function is for testing only delete later pls thnks
    static async getAllUsers(): Promise<Users[]> {
        return await prismaClient.users.findMany({
          select: {
            user_id: true,
            username: true,
            password: true,
            token: true,
          },
        });
      }
}