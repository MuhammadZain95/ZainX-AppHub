package com.example

import com.example.data.ZainXRepository
import com.example.model.AppCategory
import com.example.model.AppPlatform
import com.example.model.AppStatus
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testSeededAppsLoaded() {
        val apps = ZainXRepository.apps.value
        assertTrue("Seed apps should be populated", apps.isNotEmpty())
        assertTrue("DevLens Studio should exist in seed apps", apps.any { it.name == "DevLens Studio" })
        assertTrue("Platforms should include APK and EXE", apps.any { it.platform == AppPlatform.APK } && apps.any { it.platform == AppPlatform.EXE })
    }

    @Test
    fun testToggleWishlist() {
        val initialWishlist = ZainXRepository.wishlist.value
        val testAppId = "app_2"
        val wasWishlisted = initialWishlist.contains(testAppId)

        ZainXRepository.toggleWishlist(testAppId)
        val updatedWishlist = ZainXRepository.wishlist.value
        assertEquals(!wasWishlisted, updatedWishlist.contains(testAppId))
    }

    @Test
    fun testPublishAppAndDownloadCounter() {
        val initialCount = ZainXRepository.apps.value.size
        val newApp = ZainXRepository.publishApp(
            name = "TestApp CLI",
            tagline = "Command line suite",
            description = "High speed terminal tools",
            platform = AppPlatform.APK,
            category = AppCategory.DEVELOPER_TOOLS,
            version = "1.0.0",
            fileSizeMb = 12.5,
            changelog = "First release",
            websiteUrl = "https://zainx.io",
            githubUrl = "https://github.com/zainx",
            status = AppStatus.PUBLISHED
        )

        assertEquals(initialCount + 1, ZainXRepository.apps.value.size)
        assertEquals(0, newApp.downloadCount)

        ZainXRepository.recordDownload(newApp.id)
        val fetched = ZainXRepository.apps.value.find { it.id == newApp.id }
        assertNotNull(fetched)
        assertEquals(1, fetched!!.downloadCount)
    }

    @Test
    fun testAddReviewUpdatesRating() {
        val targetApp = ZainXRepository.apps.value.first()
        val initialReviewCount = targetApp.reviews.size

        ZainXRepository.addReview(targetApp.id, 5, "Remarkable performance!")
        val updatedApp = ZainXRepository.apps.value.find { it.id == targetApp.id }!!

        assertEquals(initialReviewCount + 1, updatedApp.reviews.size)
        assertEquals("Remarkable performance!", updatedApp.reviews.first().comment)
    }
}
