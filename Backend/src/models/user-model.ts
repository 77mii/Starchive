import { Users } from "@prisma/client"

export interface RegisterUserRequest {
    username: string
    password: string
}

export interface UserResponse {
    token?: string
    username: string
}

export interface LoginUserRequest {
    username: string
    password: string
}

export function toUserResponse(prismaUser: Users): UserResponse {
    return {
        token: prismaUser.token ?? "",
        username: prismaUser.username,
    }
}