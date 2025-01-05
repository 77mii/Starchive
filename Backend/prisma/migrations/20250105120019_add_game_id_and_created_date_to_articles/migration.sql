/*
  Warnings:

  - Added the required column `game_id` to the `Articles` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Articles" ADD COLUMN     "createdDate" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "game_id" INTEGER NOT NULL;

-- AddForeignKey
ALTER TABLE "Articles" ADD CONSTRAINT "Articles_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;
