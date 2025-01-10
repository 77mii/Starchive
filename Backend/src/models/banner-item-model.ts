import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export interface BannerItem {
  banner_id: number;
  item_id: number;
  acquire_rate: number;
}

export async function createBannerItem(data: BannerItem) {
  return await prisma.bannerItems.create({
    data,
  });
}

export async function getBannerItem(banner_id: number, item_id: number) {
  return await prisma.bannerItems.findUnique({
    where: {
      banner_id_item_id: {
        banner_id,
        item_id,
      },
    },
  });
}

export async function updateBannerItem(banner_id: number, item_id: number, data: Partial<BannerItem>) {
  return await prisma.bannerItems.update({
    where: {
      banner_id_item_id: {
        banner_id,
        item_id,
      },
    },
    data,
  });
}

export async function deleteBannerItem(banner_id: number, item_id: number) {
  return await prisma.bannerItems.delete({
    where: {
      banner_id_item_id: {
        banner_id,
        item_id,
      },
    },
  });
}

export async function getAllItemsInBanner(banner_id: number) {
    return await prisma.bannerItems.findMany({
      where: {
        banner_id,
      },
    });
  }