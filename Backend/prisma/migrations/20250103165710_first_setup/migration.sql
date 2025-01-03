-- CreateTable
CREATE TABLE "Users" (
    "user_id" SERIAL NOT NULL,
    "username" TEXT NOT NULL,
    "password" TEXT NOT NULL,

    CONSTRAINT "Users_pkey" PRIMARY KEY ("user_id")
);

-- CreateTable
CREATE TABLE "Games" (
    "game_id" SERIAL NOT NULL,
    "game_name" TEXT NOT NULL,
    "income" DOUBLE PRECISION NOT NULL,
    "description" TEXT NOT NULL,
    "currency_name" TEXT NOT NULL,
    "tickets_name" TEXT,

    CONSTRAINT "Games_pkey" PRIMARY KEY ("game_id")
);

-- CreateTable
CREATE TABLE "Banners" (
    "banner_id" SERIAL NOT NULL,
    "game_id" INTEGER NOT NULL,
    "banner_name" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "start_date" TIMESTAMP(3) NOT NULL,
    "end_date" TIMESTAMP(3) NOT NULL,
    "hard_pity" INTEGER,
    "soft_pity" INTEGER,

    CONSTRAINT "Banners_pkey" PRIMARY KEY ("banner_id")
);

-- CreateTable
CREATE TABLE "Budget" (
    "budget_id" SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "game_id" INTEGER NOT NULL,
    "allocated_budget" DOUBLE PRECISION NOT NULL,
    "allocated_currency" DOUBLE PRECISION NOT NULL,
    "allocated_tickets" DOUBLE PRECISION NOT NULL,
    "remaining_budget" DOUBLE PRECISION NOT NULL,
    "remaining_currency" DOUBLE PRECISION NOT NULL,
    "remaining_tickets" DOUBLE PRECISION NOT NULL,

    CONSTRAINT "Budget_pkey" PRIMARY KEY ("budget_id")
);

-- CreateTable
CREATE TABLE "Plans" (
    "plan_id" SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "game_id" INTEGER NOT NULL,
    "plan_description" TEXT NOT NULL,
    "plan_budget" DOUBLE PRECISION NOT NULL,
    "plan_currency" DOUBLE PRECISION NOT NULL,
    "plan_tickets" DOUBLE PRECISION NOT NULL,

    CONSTRAINT "Plans_pkey" PRIMARY KEY ("plan_id")
);

-- CreateTable
CREATE TABLE "Expenses" (
    "expense_id" SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "game_id" INTEGER NOT NULL,
    "spent_currency" DOUBLE PRECISION NOT NULL,
    "spent_money" DOUBLE PRECISION NOT NULL,
    "spent_tickets" DOUBLE PRECISION NOT NULL,

    CONSTRAINT "Expenses_pkey" PRIMARY KEY ("expense_id")
);

-- CreateTable
CREATE TABLE "Items" (
    "item_id" SERIAL NOT NULL,
    "rarity" TEXT NOT NULL,
    "item_name" TEXT NOT NULL,

    CONSTRAINT "Items_pkey" PRIMARY KEY ("item_id")
);

-- CreateTable
CREATE TABLE "BannerItems" (
    "banner_id" INTEGER NOT NULL,
    "item_id" INTEGER NOT NULL,
    "acquire_rate" DOUBLE PRECISION NOT NULL,

    CONSTRAINT "BannerItems_pkey" PRIMARY KEY ("banner_id","item_id")
);

-- CreateTable
CREATE TABLE "HardPity" (
    "pity_id" SERIAL NOT NULL,
    "user_id" INTEGER NOT NULL,
    "game_id" INTEGER NOT NULL,
    "banner_id" INTEGER NOT NULL,
    "pull_towards_pity" INTEGER NOT NULL,

    CONSTRAINT "HardPity_pkey" PRIMARY KEY ("pity_id")
);

-- CreateTable
CREATE TABLE "UserGames" (
    "user_id" INTEGER NOT NULL,
    "game_id" INTEGER NOT NULL,

    CONSTRAINT "UserGames_pkey" PRIMARY KEY ("user_id","game_id")
);

-- AddForeignKey
ALTER TABLE "Banners" ADD CONSTRAINT "Banners_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Budget" ADD CONSTRAINT "Budget_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "Users"("user_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Budget" ADD CONSTRAINT "Budget_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Plans" ADD CONSTRAINT "Plans_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "Users"("user_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Plans" ADD CONSTRAINT "Plans_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Expenses" ADD CONSTRAINT "Expenses_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "Users"("user_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Expenses" ADD CONSTRAINT "Expenses_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "BannerItems" ADD CONSTRAINT "BannerItems_banner_id_fkey" FOREIGN KEY ("banner_id") REFERENCES "Banners"("banner_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "BannerItems" ADD CONSTRAINT "BannerItems_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "Items"("item_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "HardPity" ADD CONSTRAINT "HardPity_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "Users"("user_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "HardPity" ADD CONSTRAINT "HardPity_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "HardPity" ADD CONSTRAINT "HardPity_banner_id_fkey" FOREIGN KEY ("banner_id") REFERENCES "Banners"("banner_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "UserGames" ADD CONSTRAINT "UserGames_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "Users"("user_id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "UserGames" ADD CONSTRAINT "UserGames_game_id_fkey" FOREIGN KEY ("game_id") REFERENCES "Games"("game_id") ON DELETE RESTRICT ON UPDATE CASCADE;
