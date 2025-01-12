

import { NextFunction, Request, Response } from "express";
import { BannerItem } from "../models/banner-item-model";
import { BannerItemService } from "../services/banner-item-service";

export class BannerItemController {
  static async create(req: Request, res: Response, next: NextFunction) {
    try {
      const request: BannerItem = req.body as BannerItem;
      const response = await BannerItemService.create(request);

      res.status(200).json({
        data: response,
      });
    } catch (error) {
      next(error);
    }
  }

  static async getByBannerId(req: Request, res: Response, next: NextFunction) {
    try {
      const bannerId = parseInt(req.params.bannerId);
      const response = await BannerItemService.getAllItemsInBanner(bannerId);

      res.status(200).json({
        data: response,
      });
    } catch (error) {
      next(error);
    }
  }

  static async get(req: Request, res: Response, next: NextFunction) {
    try {
      const bannerId = parseInt(req.params.bannerId);
      const itemId = parseInt(req.params.itemId);
      const response = await BannerItemService.get(bannerId, itemId);

      res.status(200).json({
        data: response,
      });
    } catch (error) {
      next(error);
    }
  }

  static async update(req: Request, res: Response, next: NextFunction) {
    try {
      const bannerId = parseInt(req.params.bannerId);
      const itemId = parseInt(req.params.itemId);
      const request = req.body as Partial<BannerItem>;
      const response = await BannerItemService.update(bannerId, itemId, request);

      res.status(200).json({
        data: response,
      });
    } catch (error) {
      next(error);
    }
  }

  static async delete(req: Request, res: Response, next: NextFunction) {
    try {
      const bannerId = parseInt(req.params.bannerId);
      const itemId = parseInt(req.params.itemId);
      const response = await BannerItemService.delete(bannerId, itemId);

      res.status(200).json({
        data: response,
      });
    } catch (error) {
      next(error);
    }
  }
}