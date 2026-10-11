package com.example.model

enum class AppPlatform(val displayName: String, val badgeColorHex: Long, val extension: String) {
    APK("Android APK", 0xFF00E5A0, ".apk"),
    AAB("Android AAB", 0xFF19B8FF, ".aab"),
    EXE("Windows EXE", 0xFF0066FF, ".exe"),
    MSI("Windows MSI", 0xFF8B3DFF, ".msi"),
    ZIP("Universal ZIP", 0xFFFFB020, ".zip")
}

enum class AppCategory(val displayName: String) {
    ALL("All Categories"),
    DEVELOPER_TOOLS("Developer Tools"),
    AI_UTILITIES("AI & Utilities"),
    PRODUCTIVITY("Productivity"),
    GAMES("Games & Fun"),
    MEDIA_AUDIO("Media & Audio"),
    SECURITY("Security & Privacy"),
    DESIGN("Design & 3D")
}

enum class AppStatus(val displayName: String) {
    PUBLISHED("Published"),
    IN_REVIEW("In Review"),
    DRAFT("Draft")
}

enum class UserRole(val displayName: String) {
    USER("Community User"),
    DEVELOPER("Verified Developer"),
    ADMIN("Platform Admin")
}

data class AppVersion(
    val versionNumber: String,
    val releaseDate: String,
    val changelog: String,
    val sizeMb: Double,
    val downloadUrl: String
)

data class Review(
    val id: String,
    val appId: String,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: String,
    val helpfulCount: Int = 0
)

data class AppItem(
    val id: String,
    val name: String,
    val tagline: String,
    val description: String,
    val developerName: String,
    val developerId: String,
    val platform: AppPlatform,
    val category: AppCategory,
    val version: String,
    val fileSizeMb: Double,
    val downloadCount: Int,
    val rating: Float,
    val ratingCount: Int,
    val iconColor: Long, // Hex color for procedural futuristic icon badge
    val iconSymbol: String, // 2-3 letter symbol or emoji
    val changelog: String,
    val websiteUrl: String = "https://zainx.io",
    val githubUrl: String = "https://github.com",
    val isVerified: Boolean = true,
    val isFeatured: Boolean = false,
    val status: AppStatus = AppStatus.PUBLISHED,
    val releaseDate: String,
    val checksumSha256: String,
    val minOs: String,
    val versions: List<AppVersion> = emptyList(),
    val reviews: List<Review> = emptyList()
)

data class User(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val role: UserRole,
    val bio: String,
    val location: String,
    val website: String,
    val joinDate: String,
    val followersCount: Int,
    val isBanned: Boolean = false
)

data class ReportItem(
    val id: String,
    val appId: String,
    val appName: String,
    val reporterEmail: String,
    val reason: String,
    val date: String,
    val isResolved: Boolean = false
)

data class PricingPlan(
    val id: String,
    val name: String,
    val priceMonthly: String,
    val description: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val ctaText: String
)
