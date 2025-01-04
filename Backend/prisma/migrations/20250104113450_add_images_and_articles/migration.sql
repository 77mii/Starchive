-- AlterTable
ALTER TABLE "Banners" ADD COLUMN     "image_url" TEXT;

-- AlterTable
ALTER TABLE "Games" ADD COLUMN     "image_url" TEXT;

-- AlterTable
ALTER TABLE "Items" ADD COLUMN     "image_url" TEXT;

-- CreateTable
CREATE TABLE "Articles" (
    "article_id" SERIAL NOT NULL,
    "title" TEXT NOT NULL,
    "text" TEXT NOT NULL,
    "image_url" TEXT,

    CONSTRAINT "Articles_pkey" PRIMARY KEY ("article_id")
);
