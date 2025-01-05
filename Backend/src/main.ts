import app from "./application/app"
import { logger } from "./application/logging"
require('dotenv').config()
app.listen(3000, () => {
    logger.info("Listening on http://localhost:3000")
})