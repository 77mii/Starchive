import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreateBannerRequest, BannerResponse, toBannerResponse } from "../models/banner-model"
import { BannerValidation } from "../validations/banner-validation"
import { Validation } from "../validations/validation"

export class BannerService {
    static async create(request: CreateBannerRequest): Promise<BannerResponse> {
        const createRequest = Validation.validate(BannerValidation.CREATE, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: createRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const banner = await prismaClient.banners.create({
            data: createRequest
        })

        return toBannerResponse(banner)
    }

    static async getByGameId(gameId: number): Promise<BannerResponse[]> {
        const banners = await prismaClient.banners.findMany({
            where: {
                game_id: gameId
            }
        })

        return banners.map(banner => toBannerResponse(banner))
    }

    static async update(bannerId: number, request: Partial<CreateBannerRequest>): Promise<BannerResponse> {
        const updateRequest = Validation.validate(BannerValidation.UPDATE, request)

        const existingBanner = await prismaClient.banners.findUnique({
            where: {
                banner_id: bannerId
            }
        })

        if (!existingBanner) {
            throw new ResponseError(404, "Banner not found")
        }

        const banner = await prismaClient.banners.update({
            where: {
                banner_id: bannerId
            },
            data: updateRequest
        })

        return toBannerResponse(banner)
    }

    static async getActive(): Promise<BannerResponse[]> {
        const currentDate = new Date()

        const banners = await prismaClient.banners.findMany({
            where: {
                AND: [
                    {
                        start_date: {
                            lte: currentDate
                        }
                    },
                    {
                        end_date: {
                            gte: currentDate
                        }
                    }
                ]
            }
        })

        return banners.map(banner => toBannerResponse(banner))
    }
}