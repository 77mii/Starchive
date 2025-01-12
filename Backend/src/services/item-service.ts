import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreateItemRequest, ItemResponse, toItemResponse, BannerItemResponse, toBannerItemResponse } from "../models/item-model"
import { ItemValidation } from "../validations/item-validation"
import { Validation } from "../validations/validation"

export class ItemService {
    static async create(request: CreateItemRequest): Promise<ItemResponse> {
        const createRequest = Validation.validate(ItemValidation.CREATE, request)

        const item = await prismaClient.items.create({
            data: createRequest
        })

        return toItemResponse(item)
    }

    static async getByGameId(gameId: number): Promise<ItemResponse[]> {
        const items = await prismaClient.items.findMany({
            where: {
                BannerItems: {
                    some: {
                        Banner: {
                            game_id: gameId
                        }
                    }
                }
            }
        })

        return items.map(item => toItemResponse(item))
    }

    static async getByBannerId(bannerId: number): Promise<BannerItemResponse[]> {
        const bannerItems = await prismaClient.bannerItems.findMany({
            where: {
                banner_id: bannerId
            }
        })

        return bannerItems.map(bannerItem => toBannerItemResponse(bannerItem))
    }
    static async getById(itemId: number): Promise<ItemResponse> {
        const item = await prismaClient.items.findUnique({
            where: {
                item_id: itemId
            }
        })

        if (!item) {
            throw new ResponseError(404, "Item not found")
        }

        return toItemResponse(item)
    }
}