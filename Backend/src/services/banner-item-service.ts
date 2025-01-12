

import { prismaClient } from "../application/database";
import { ResponseError } from "../error/response-error";
import { BannerItem } from "../models/banner-item-model";
import { BannerItemValidation } from "../validations/banner-item-validation";
import { Validation } from "../validations/validation";

export class BannerItemService {
  static async create(data: BannerItem) {
    const validatedData = Validation.validate(BannerItemValidation.CREATE, data);

    const banner = await prismaClient.banners.findUnique({
      where: {
        banner_id: validatedData.banner_id,
      },
    });

    if (!banner) {
      throw new ResponseError(404, "Banner not found");
    }

    const item = await prismaClient.items.findUnique({
      where: {
        item_id: validatedData.item_id,
      },
    });

    if (!item) {
      throw new ResponseError(404, "Item not found");
    }

    return await prismaClient.bannerItems.create({
      data: validatedData,
    });
  }

  static async get(banner_id: number, item_id: number) {
    const bannerItem = await prismaClient.bannerItems.findUnique({
      where: {
        banner_id_item_id: {
          banner_id,
          item_id,
        },
      },
    });

    if (!bannerItem) {
      throw new ResponseError(404, "BannerItem not found");
    }

    return bannerItem;
  }

  static async update(banner_id: number, item_id: number, data: Partial<BannerItem>) {
    const validatedData = Validation.validate(BannerItemValidation.UPDATE, data);

    const bannerItem = await prismaClient.bannerItems.findUnique({
      where: {
        banner_id_item_id: {
          banner_id,
          item_id,
        },
      },
    });

    if (!bannerItem) {
      throw new ResponseError(404, "BannerItem not found");
    }

    return await prismaClient.bannerItems.update({
      where: {
        banner_id_item_id: {
          banner_id,
          item_id,
        },
      },
      data: validatedData,
    });
  }

  static async delete(banner_id: number, item_id: number) {
    const bannerItem = await prismaClient.bannerItems.findUnique({
      where: {
        banner_id_item_id: {
          banner_id,
          item_id,
        },
      },
    });

    if (!bannerItem) {
      throw new ResponseError(404, "BannerItem not found");
    }

    return await prismaClient.bannerItems.delete({
      where: {
        banner_id_item_id: {
          banner_id,
          item_id,
        },
      },
    });
  }

  static async getAllItemsInBanner(banner_id: number) {
    const bannerItems = await prismaClient.bannerItems.findMany({
      where: {
        banner_id,
      },
    });

    if (bannerItems.length === 0) {
      throw new ResponseError(404, "No items found for this banner");
    }

    return bannerItems;
  }
}