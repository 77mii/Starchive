import { Users } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreatePityRequest, PityResponse, toPityResponse, UpdatePityRequest } from "../models/pity-model"
import { PityValidation } from "../validations/pity-validation"
import { Validation } from "../validations/validation"

export class PityService {
    static async create(user: Users, request: CreatePityRequest): Promise<PityResponse> {
        const createRequest = Validation.validate(PityValidation.CREATE, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: createRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const banner = await prismaClient.banners.findUnique({
            where: {
                banner_id: createRequest.banner_id
            }
        })

        if (!banner) {
            throw new ResponseError(404, "Banner not found")
        }

        const pity = await prismaClient.hardPity.create({
            data: {
                user_id: user.user_id,
                game_id: createRequest.game_id,
                banner_id: createRequest.banner_id,
                pull_towards_pity: createRequest.pull_towards_pity
            }
        })

        return toPityResponse(pity)
    }

    static async getByBannerId(user: Users, bannerId: number): Promise<PityResponse[]> {
        const pities = await prismaClient.hardPity.findMany({
            where: {
                user_id: user.user_id,
                banner_id: bannerId
            }
        })

        return pities.map(pity => toPityResponse(pity))
    }

    static async update(pityId: number, request: UpdatePityRequest): Promise<PityResponse> {
        const updateRequest = Validation.validate(PityValidation.UPDATE, request)

        const existingPity = await prismaClient.hardPity.findUnique({
            where: {
                pity_id: pityId
            }
        })

        if (!existingPity) {
            throw new ResponseError(404, "Pity not found")
        }

        const pity = await prismaClient.hardPity.update({
            where: {
                pity_id: pityId
            },
            data: updateRequest
        })

        return toPityResponse(pity)
    }
}