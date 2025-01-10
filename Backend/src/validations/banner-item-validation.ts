import { z, ZodType } from "zod";

export class BannerItemValidation {
  static readonly CREATE: ZodType = z.object({
    banner_id: z.number().int().min(1),
    item_id: z.number().int().min(1),
    acquire_rate: z.number().min(0).max(1),
  });

  static readonly UPDATE: ZodType = z.object({
    acquire_rate: z.number().min(0).max(1).optional(),
  });
}