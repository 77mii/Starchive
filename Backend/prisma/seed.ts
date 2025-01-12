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

  // Create a banner
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

  // Create an item
  const item = await prisma.items.create({
    data: {
      rarity: 5,
      item_name: "Sword of Destiny",
      image_url: "https://example.com/sword-of-destiny.jpg",
    },
  });

  // Add the item to both banners
  await prisma.bannerItems.create({
    data: {
      banner_id: banner1.banner_id,
      item_id: item.item_id,
      acquire_rate: 0.5, // Example acquire rate
    },
  });

  await prisma.bannerItems.create({
    data: {
      banner_id: banner2.banner_id,
      item_id: item.item_id,
      acquire_rate: 0.5, // Example acquire rate
    },
  });

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