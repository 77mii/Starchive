import { PrismaClient } from "@prisma/client";
import bcrypt from "bcrypt";
import { v4 as uuid } from "uuid";

const prisma = new PrismaClient();

async function main() {
  // Create a user
  const passwordHash = await bcrypt.hash("password123", 10);
  const user = await prisma.users.create({
    data: {
      username: "testuser",
      password: passwordHash,
      token: uuid(),
    },
  });

  // Create a game
  const game = await prisma.games.create({
    data: {
      game_name: "Honkai: Star Rail",
      income: 1680,
      description: "A Space Fantasy Turn Based RPG by Hoyoverse",
      currency_name: "Stellar Jades",
      tickets_name: "Star Rail Passes",
      image_url: "R.drawable.honkai_star_rail",
    },
  });

  // Create banners
  const banner1 = await prisma.banners.create({
    data: {
      game_id: game.game_id,
      banner_name: "A Long Voyage Home",
      type: "Limited Character",
      start_date: new Date("2025-01-01T00:00:00.000Z"),
      end_date: new Date("2025-01-15T23:59:59.999Z"),
      hard_pity: 90,
      soft_pity: 76,
      image_url: "https://www.lapakgaming.com/blog/id-id/wp-content/uploads/2024/12/Fugue-Honkai-Star-Rail.jpg",
    },
  });

  const banner2 = await prisma.banners.create({
    data: {
      game_id: game.game_id,
      banner_name: "Firefull Flyshine",
      type: "Limited Character Rerun",
      start_date: new Date("2025-01-01T00:00:00.000Z"),
      end_date: new Date("2025-01-15T23:59:59.999Z"),
      hard_pity: 90,
      soft_pity: 76,
      image_url: "https://cdn.oneesports.id/cdn-data/sites/2/2024/05/honkai_star_rail_firefly_drip_marketing-1024x576-1-450x253.jpg",
    },
  });

  // Create a user-game relation
  await prisma.userGames.create({
    data: {
      user_id: user.user_id,
      game_id: game.game_id,
    },
  });

  // Create a budget
  await prisma.budget.create({
    data: {
      user_id: user.user_id,
      game_id: game.game_id,
      allocated_budget: 100000,
      allocated_currency: 5000,
      allocated_tickets: 50,
      remaining_budget: 20000,
      remaining_currency: 3400,
      remaining_tickets: 0,
    },
  });

  // Create items
  const items = [
    {
      rarity: 5,
      item_name: "Sword of Destiny",
      image_url: "https://example.com/sword-of-destiny.jpg",
    },
    {
      rarity: 4,
      item_name: "Shield of Valor",
      image_url: "https://example.com/shield-of-valor.jpg",
    },
    {
      rarity: 3,
      item_name: "Helmet of Wisdom",
      image_url: "https://example.com/helmet-of-wisdom.jpg",
    },
    {
      rarity: 5,
      item_name: "Bow of Eternity",
      image_url: "https://example.com/bow-of-eternity.jpg",
    },
    {
      rarity: 4,
      item_name: "Staff of Power",
      image_url: "https://example.com/staff-of-power.jpg",
    },
    {
      rarity: 3,
      item_name: "Dagger of Speed",
      image_url: "https://example.com/dagger-of-speed.jpg",
    },
  ];

  const createdItems = await Promise.all(
    items.map((item) => prisma.items.create({ data: item }))
  );

  // Add items to banners
  const bannerItems = [
    { banner_id: banner1.banner_id, item_id: createdItems[0].item_id, acquire_rate: 0.006 }, // 0.6%
    { banner_id: banner1.banner_id, item_id: createdItems[1].item_id, acquire_rate: 0.3 },   // 30%
    { banner_id: banner1.banner_id, item_id: createdItems[2].item_id, acquire_rate: 0.694 }, // 69.4%
    { banner_id: banner2.banner_id, item_id: createdItems[3].item_id, acquire_rate: 0.006 }, // 0.6%
    { banner_id: banner2.banner_id, item_id: createdItems[4].item_id, acquire_rate: 0.3 },   // 30%
    { banner_id: banner2.banner_id, item_id: createdItems[5].item_id, acquire_rate: 0.694 }, // 69.4%
  ];

  await Promise.all(
    bannerItems.map((bannerItem) => prisma.bannerItems.create({ data: bannerItem }))
  );

  await prisma.hardPity.create({
    data: {
      user_id: user.user_id,
      game_id: game.game_id,
      banner_id: banner1.banner_id,
      pull_towards_pity: 45, // Example pull towards pity
    },
  });

  // Create articles
  await prisma.articles.create({
    data: {
      title: "HSR Patch 3.0",
      text: "Amphoreus is coming. Lorem ipsum dolor sit amet.",
      image_url: "R.drawable.article1",
      game_id: game.game_id,
      createdDate: new Date(),
    },
  });

  await prisma.articles.create({
    data: {
      title: "HSR Patch 3.1",
      text: "Amphoreus continues. Lorem ipsum dolor sit amet.",
      image_url: "R.drawable.article2",
      game_id: game.game_id,
      createdDate: new Date(),
    },
  });

  await prisma.articles.create({
    data: {
      title: "HSR Patch 3.2",
      text: "New character preview. Lorem ipsum dolor sit amet.",
      image_url: "R.drawable.article3",
      game_id: game.game_id,
      createdDate: new Date(),
    },
  });

  console.log("Seed data created successfully");
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });