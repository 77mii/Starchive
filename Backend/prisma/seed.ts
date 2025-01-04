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
      game_name: "Genshin Impact",
      income: 1000,
      description: "Open world anime game",
      currency_name: "Primogems",
      tickets_name: "Intertwined Fate",
      image_url: "https://example.com/genshin-impact.jpg",
    },
  });

  // Create a banner
  const banner = await prisma.banners.create({
    data: {
      game_id: game.game_id,
      banner_name: "New Year Banner",
      type: "Event",
      start_date: new Date("2025-01-01T00:00:00.000Z"),
      end_date: new Date("2025-01-15T23:59:59.999Z"),
      hard_pity: 90,
      soft_pity: 75,
      image_url: "https://example.com/new-year-banner.jpg",
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
      allocated_budget: 1000,
      allocated_currency: 5000,
      allocated_tickets: 50,
      remaining_budget: 800,
      remaining_currency: 4000,
      remaining_tickets: 40,
    },
  });

  // Create an item
  const item = await prisma.items.create({
    data: {
      rarity: "Rare",
      item_name: "Sword of Destiny",
      image_url: "https://example.com/sword-of-destiny.jpg",
    },
  });

  // Create an article
  await prisma.articles.create({
    data: {
      title: "Genshin Impact Tips",
      text: "Here are some tips for playing Genshin Impact...",
      image_url: "https://example.com/genshin-impact-tips.jpg",
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