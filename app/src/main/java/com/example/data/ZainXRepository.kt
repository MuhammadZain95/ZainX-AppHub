package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

object ZainXRepository {

    private val _currentUser = MutableStateFlow(
        User(
            id = "usr_zain_dev",
            name = "Alex Vance",
            username = "alexvance",
            email = "alex.vance@zainx.io",
            role = UserRole.DEVELOPER,
            bio = "Building high-performance tools for cross-platform developers and creators. Fan of clean code & low latency.",
            location = "San Francisco, CA",
            website = "https://alexvance.tech",
            joinDate = "March 2025",
            followersCount = 428
        )
    )
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _apps = MutableStateFlow<List<AppItem>>(emptyList())
    val apps: StateFlow<List<AppItem>> = _apps.asStateFlow()

    private val _wishlist = MutableStateFlow<Set<String>>(setOf("app_1", "app_4"))
    val wishlist: StateFlow<Set<String>> = _wishlist.asStateFlow()

    private val _followingDevelopers = MutableStateFlow<Set<String>>(setOf("usr_dev_1"))
    val followingDevelopers: StateFlow<Set<String>> = _followingDevelopers.asStateFlow()

    private val _reports = MutableStateFlow<List<ReportItem>>(emptyList())
    val reports: StateFlow<List<ReportItem>> = _reports.asStateFlow()

    // Site settings managed by Admin
    private val _siteName = MutableStateFlow("ZainX AppHub")
    val siteName: StateFlow<String> = _siteName.asStateFlow()

    private val _maintenanceMode = MutableStateFlow(false)
    val maintenanceMode: StateFlow<Boolean> = _maintenanceMode.asStateFlow()

    private val _maxUploadSizeMb = MutableStateFlow(500)
    val maxUploadSizeMb: StateFlow<Int> = _maxUploadSizeMb.asStateFlow()

    init {
        seedInitialData()
    }

    private fun seedInitialData() {
        val initialApps = listOf(
            AppItem(
                id = "app_1",
                name = "DevLens Studio",
                tagline = "Visual mobile code inspector, network sniffer & telemetry analyzer",
                description = "DevLens Studio gives mobile developers instant observability into runtime memory, network requests, layout hierarchies, and SQLite databases without installing invasive SDKs.\n\nKey Highlights:\n• Zero-dependency network inspection with live HAR recording\n• 3D interactive Compose view hierarchy tree\n• Real-time coroutine thread inspector and memory leak detector\n• Built-in mock API server with latency simulation",
                developerName = "HyperVibe Labs",
                developerId = "usr_dev_1",
                platform = AppPlatform.APK,
                category = AppCategory.DEVELOPER_TOOLS,
                version = "3.2.0",
                fileSizeMb = 48.6,
                downloadCount = 384200,
                rating = 4.9f,
                ratingCount = 1840,
                iconColor = 0xFF19B8FF,
                iconSymbol = "DL",
                changelog = "v3.2.0: Added Jetpack Compose layout bounds visualizer and instant SQLite database query console.",
                releaseDate = "Oct 4, 2026",
                checksumSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                minOs = "Android 9.0 (API 28)+",
                isFeatured = true,
                versions = listOf(
                    AppVersion("3.2.0", "Oct 4, 2026", "Added Jetpack Compose layout bounds visualizer.", 48.6, "https://dl.zainx.io/app_1_v320.apk"),
                    AppVersion("3.1.2", "Sep 12, 2026", "Fixed high refresh rate rendering stutter on Fold devices.", 47.1, "https://dl.zainx.io/app_1_v312.apk"),
                    AppVersion("3.0.0", "Jul 28, 2026", "Major rewrite with new dark glass UI and instant export.", 45.0, "https://dl.zainx.io/app_1_v300.apk")
                ),
                reviews = listOf(
                    Review("rev_1", "app_1", "Sarah Connor", 5, "Indispensable tool! Replaced three different debugging proxies for our team.", "Oct 6, 2026", 42),
                    Review("rev_2", "app_1", "Marcus Reid", 5, "The 3D Compose hierarchy debugger is absolute magic.", "Oct 5, 2026", 19),
                    Review("rev_3", "app_1", "Elena Rostova", 4, "Extremely polished. Would love to see WebSocket frame inspector in next release.", "Sep 28, 2026", 8)
                )
            ),
            AppItem(
                id = "app_2",
                name = "NovaFlow Pro",
                tagline = "Node-based real-time desktop automation and AI workflow orchestrator",
                description = "NovaFlow Pro connects your desktop applications, web APIs, and local LLMs through a fluid node-graph interface. Automate file pipelines, batch media processing, and complex data extraction in seconds.\n\nFeatures:\n• 120+ pre-built integration nodes (Git, Docker, Photoshop, Excel, REST)\n• High-throughput execution engine utilizing SIMD acceleration\n• Sandboxed Python script execution node\n• End-to-end encrypted workflow export and team sharing",
                developerName = "Nexus Synthetics",
                developerId = "usr_dev_2",
                platform = AppPlatform.EXE,
                category = AppCategory.PRODUCTIVITY,
                version = "2.8.4",
                fileSizeMb = 142.3,
                downloadCount = 215000,
                rating = 4.8f,
                ratingCount = 920,
                iconColor = 0xFF8B3DFF,
                iconSymbol = "NF",
                changelog = "v2.8.4: Multi-threaded canvas rendering, new CSV parser node, and Windows 11 Fluent glass mica support.",
                releaseDate = "Sep 29, 2026",
                checksumSha256 = "8f434346648f6b96df89dda901c5176b10a6d83961dd3c1ac88b59b2dc327aa4",
                minOs = "Windows 10/11 x64",
                isFeatured = true,
                versions = listOf(
                    AppVersion("2.8.4", "Sep 29, 2026", "Mica blur support and high throughput engine.", 142.3, "https://dl.zainx.io/app_2_v284.exe"),
                    AppVersion("2.7.0", "Aug 15, 2026", "Local LLM node integration.", 138.0, "https://dl.zainx.io/app_2_v270.exe")
                ),
                reviews = listOf(
                    Review("rev_4", "app_2", "David K.", 5, "Automated 4 hours of weekly video rendering and metadata tagging. Superb software.", "Oct 1, 2026", 31)
                )
            ),
            AppItem(
                id = "app_3",
                name = "CryptoVault Key",
                tagline = "Air-gapped zero-knowledge password and 2FA secrets vault",
                description = "CryptoVault Key operates completely offline with post-quantum Kyber + ChaCha20-Poly1305 multi-layer encryption. Your master keys never touch external networks.",
                developerName = "ZeroTrust Labs",
                developerId = "usr_dev_3",
                platform = AppPlatform.ZIP,
                category = AppCategory.SECURITY,
                version = "5.1.0",
                fileSizeMb = 24.1,
                downloadCount = 189400,
                rating = 4.95f,
                ratingCount = 1420,
                iconColor = 0xFF00E5A0,
                iconSymbol = "CV",
                changelog = "v5.1.0: Post-quantum Kyber1024 encryption layer integration and hardware YubiKey FIDO2 support.",
                releaseDate = "Oct 2, 2026",
                checksumSha256 = "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9",
                minOs = "Cross-Platform (Linux/Mac/Win)",
                isFeatured = true,
                versions = listOf(
                    AppVersion("5.1.0", "Oct 2, 2026", "Kyber1024 post-quantum defense.", 24.1, "https://dl.zainx.io/app_3_v510.zip")
                ),
                reviews = listOf(
                    Review("rev_5", "app_3", "SecurityChief_01", 5, "Audited the source binary: clean, zero outgoing telemetry, flawless cryptographic primitives.", "Oct 3, 2026", 67)
                )
            ),
            AppItem(
                id = "app_4",
                name = "Zenith Audio Engine",
                tagline = "Ultra low-latency spatial synthesizer and modular DAW",
                description = "Zenith is a professional digital audio workstation engineered from scratch in Rust and C++ with custom DSP pipelines. Features 64-bit floating point processing, 32-track polyphonic synthesizer, and spatial VST3 plugin hosting.",
                developerName = "SonicAura DSP",
                developerId = "usr_dev_4",
                platform = AppPlatform.MSI,
                category = AppCategory.MEDIA_AUDIO,
                version = "4.0.2",
                fileSizeMb = 312.0,
                downloadCount = 98000,
                rating = 4.7f,
                ratingCount = 580,
                iconColor = 0xFFFF3D8B,
                iconSymbol = "ZA",
                changelog = "v4.0.2: ASIO 2.3 ultra-low 1.2ms buffer mode, spatial audio panning, and 48 new analog oscillator patches.",
                releaseDate = "Sep 18, 2026",
                checksumSha256 = "11a49f33f6a223807366b7b113b4c475f816fc69dd63514080d329186411a22d",
                minOs = "Windows 11 x64 / ASIO driver",
                isFeatured = true,
                versions = listOf(
                    AppVersion("4.0.2", "Sep 18, 2026", "Spatial audio panning engine.", 312.0, "https://dl.zainx.io/app_4_v402.msi")
                ),
                reviews = listOf(
                    Review("rev_6", "app_4", "BeatCrafter", 5, "The latency on my RTX system is non-existent. Great audio fidelity.", "Sep 22, 2026", 15)
                )
            ),
            AppItem(
                id = "app_5",
                name = "AeroBrowser Ultra",
                tagline = "Ultralight privacy-first Android browser with zero tracking",
                description = "Engineered for speed, privacy, and battery efficiency. AeroBrowser features hardware-accelerated ad blocking, automatic fingerprint obfuscation, and native reader mode.",
                developerName = "AeroSoft Foundation",
                developerId = "usr_dev_5",
                platform = AppPlatform.APK,
                category = AppCategory.AI_UTILITIES,
                version = "12.4.1",
                fileSizeMb = 18.2,
                downloadCount = 492000,
                rating = 4.6f,
                ratingCount = 3100,
                iconColor = 0xFF19B8FF,
                iconSymbol = "AB",
                changelog = "v12.4.1: Upgraded engine core, reduced RAM consumption by 22%, added dark mode AMOLED forcing.",
                releaseDate = "Oct 7, 2026",
                checksumSha256 = "7d793037a0760186574b0282f2f435e70ec0e545589ba7ac69b22292fc81bc0a",
                minOs = "Android 8.0+",
                isFeatured = false,
                versions = listOf(
                    AppVersion("12.4.1", "Oct 7, 2026", "Reduced RAM consumption.", 18.2, "https://dl.zainx.io/app_5_v1241.apk")
                ),
                reviews = listOf(
                    Review("rev_7", "app_5", "Kiran Patel", 5, "Loads webpages twice as fast as Chrome. My go-to browser.", "Oct 8, 2026", 28)
                )
            ),
            AppItem(
                id = "app_6",
                name = "PixelForge 3D",
                tagline = "Mobile voxel sculptor, vertex painter & GLTF/OBJ exporter",
                description = "Transform your Android tablet or phone into a portable 3D modeling studio. Sculpt voxels with dynamic brush radius, apply PBR materials, and export rigged meshes directly to Blender or Unreal.",
                developerName = "VertexCraft Interactive",
                developerId = "usr_dev_6",
                platform = AppPlatform.AAB,
                category = AppCategory.DESIGN,
                version = "2.1.0",
                fileSizeMb = 86.4,
                downloadCount = 143000,
                rating = 4.85f,
                ratingCount = 890,
                iconColor = 0xFFFFB020,
                iconSymbol = "PF",
                changelog = "v2.1.0: Real-time ray-traced preview for devices with Vulkan 1.3, stylus pressure sensitivity curves.",
                releaseDate = "Sep 25, 2026",
                checksumSha256 = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae",
                minOs = "Android 11+ (Vulkan Recommended)",
                isFeatured = false,
                versions = listOf(
                    AppVersion("2.1.0", "Sep 25, 2026", "Vulkan 1.3 ray tracing.", 86.4, "https://dl.zainx.io/app_6_v210.aab")
                ),
                reviews = listOf(
                    Review("rev_8", "app_6", "Chloe Chen", 5, "Created game assets on my flight without needing my laptop. Sensational app.", "Sep 27, 2026", 24)
                )
            ),
            AppItem(
                id = "app_7",
                name = "OmniTerminal Core",
                tagline = "GPU-accelerated modern terminal with multiplexing & ssh manager",
                description = "Blazing fast DirectX/Metal rendered terminal featuring built-in split panes, quake dropdown mode, image rendering via sixel, and cloud-synced SSH bookmark manager with biometric lock.",
                developerName = "OmniTech Software",
                developerId = "usr_dev_7",
                platform = AppPlatform.EXE,
                category = AppCategory.DEVELOPER_TOOLS,
                version = "3.5.2",
                fileSizeMb = 65.0,
                downloadCount = 162000,
                rating = 4.9f,
                ratingCount = 1120,
                iconColor = 0xFF00E5A0,
                iconSymbol = "OT",
                changelog = "v3.5.2: Smooth ligature rendering, customizable shader effects (CRT scanline), and PowerShell 7.5 integration.",
                releaseDate = "Oct 1, 2026",
                checksumSha256 = "e2d4243b821a0f9a2b5357876a38202d6b35d648b7882fb322a30bb0b218a5bc",
                minOs = "Windows 10/11 x64",
                isFeatured = false,
                versions = listOf(
                    AppVersion("3.5.2", "Oct 1, 2026", "PowerShell 7.5 integration.", 65.0, "https://dl.zainx.io/app_7_v352.exe")
                ),
                reviews = listOf(
                    Review("rev_9", "app_7", "Jason Bourne", 5, "Best terminal on Windows hands down. The GPU rendering makes scrolling thousands of logs buttery smooth.", "Oct 3, 2026", 18)
                )
            ),
            AppItem(
                id = "app_8",
                name = "SoundWave Synthesizer",
                tagline = "Modular subtractive & FM synthesizer suite with MIDI 2.0",
                description = "Open source synthesizer package featuring 16 voices of polyphony, dual multimode filters, 4 envelope generators, 3 LFOs, and a flexible 32-slot modulation matrix.",
                developerName = "WaveForm Collective",
                developerId = "usr_dev_8",
                platform = AppPlatform.ZIP,
                category = AppCategory.MEDIA_AUDIO,
                version = "1.9.0",
                fileSizeMb = 54.0,
                downloadCount = 87500,
                rating = 4.75f,
                ratingCount = 430,
                iconColor = 0xFF8B3DFF,
                iconSymbol = "SW",
                changelog = "v1.9.0: MIDI 2.0 per-note pitch bend and expression support, 60 new factory synth presets.",
                releaseDate = "Sep 15, 2026",
                checksumSha256 = "c0446738541c4d0263f31cf310f2249e083984d4d623261cf3fcdd2a20a67272",
                minOs = "Cross-Platform",
                isFeatured = false,
                versions = listOf(
                    AppVersion("1.9.0", "Sep 15, 2026", "MIDI 2.0 support.", 54.0, "https://dl.zainx.io/app_8_v190.zip")
                ),
                reviews = listOf(
                    Review("rev_10", "app_8", "Leo Synth", 5, "The filters have an unbelievable analog warmth.", "Sep 19, 2026", 11)
                )
            )
        )

        _apps.value = initialApps

        _reports.value = listOf(
            ReportItem(
                id = "rep_1",
                appId = "app_5",
                appName = "AeroBrowser Ultra",
                reporterEmail = "user92@gmail.com",
                reason = "Outdated release notes on website link",
                date = "Oct 9, 2026",
                isResolved = false
            ),
            ReportItem(
                id = "rep_2",
                appId = "app_8",
                appName = "SoundWave Synthesizer",
                reporterEmail = "producer_x@live.com",
                reason = "Minor audio crackle on 96kHz ASIO output",
                date = "Oct 8, 2026",
                isResolved = true
            )
        )
    }

    fun toggleWishlist(appId: String) {
        val current = _wishlist.value
        _wishlist.value = if (current.contains(appId)) {
            current - appId
        } else {
            current + appId
        }
    }

    fun toggleFollowDeveloper(developerId: String) {
        val current = _followingDevelopers.value
        _followingDevelopers.value = if (current.contains(developerId)) {
            current - developerId
        } else {
            current + developerId
        }
    }

    fun recordDownload(appId: String) {
        _apps.value = _apps.value.map { app ->
            if (app.id == appId) {
                app.copy(downloadCount = app.downloadCount + 1)
            } else {
                app
            }
        }
    }

    fun addReview(appId: String, rating: Int, comment: String) {
        val user = _currentUser.value
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val today = dateFormat.format(Date())
        val newReview = Review(
            id = "rev_${System.currentTimeMillis()}",
            appId = appId,
            userName = user.name,
            rating = rating,
            comment = comment,
            date = today,
            helpfulCount = 0
        )

        _apps.value = _apps.value.map { app ->
            if (app.id == appId) {
                val updatedReviews = listOf(newReview) + app.reviews
                val newCount = updatedReviews.size
                val newRating = updatedReviews.map { it.rating }.average().toFloat()
                app.copy(
                    reviews = updatedReviews,
                    rating = (newRating * 10).toInt() / 10f,
                    ratingCount = newCount
                )
            } else {
                app
            }
        }
    }

    fun publishApp(
        name: String,
        tagline: String,
        description: String,
        platform: AppPlatform,
        category: AppCategory,
        version: String,
        fileSizeMb: Double,
        changelog: String,
        websiteUrl: String,
        githubUrl: String,
        status: AppStatus
    ): AppItem {
        val user = _currentUser.value
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val today = dateFormat.format(Date())
        val randomChecksum = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "")

        val symbol = if (name.length >= 2) name.substring(0, 2).uppercase() else "ZX"
        val colors = listOf(0xFF19B8FF, 0xFF8B3DFF, 0xFF00E5A0, 0xFFFF3D8B, 0xFFFFB020)
        val assignedColor = colors[(_apps.value.size) % colors.size]

        val newApp = AppItem(
            id = "app_${System.currentTimeMillis()}",
            name = name,
            tagline = tagline,
            description = description,
            developerName = user.name,
            developerId = user.id,
            platform = platform,
            category = category,
            version = version,
            fileSizeMb = fileSizeMb,
            downloadCount = 0,
            rating = 5.0f,
            ratingCount = 1,
            iconColor = assignedColor,
            iconSymbol = symbol,
            changelog = changelog,
            websiteUrl = websiteUrl.ifBlank { "https://zainx.io" },
            githubUrl = githubUrl.ifBlank { "https://github.com" },
            isVerified = true,
            isFeatured = false,
            status = status,
            releaseDate = today,
            checksumSha256 = randomChecksum,
            minOs = when (platform) {
                AppPlatform.APK, AppPlatform.AAB -> "Android 10.0+"
                AppPlatform.EXE, AppPlatform.MSI -> "Windows 10/11 x64"
                AppPlatform.ZIP -> "Cross-Platform"
            },
            versions = listOf(
                AppVersion(version, today, changelog, fileSizeMb, "https://dl.zainx.io/${name.lowercase().replace(" ", "_")}_v${version}${platform.extension}")
            ),
            reviews = emptyList()
        )

        _apps.value = listOf(newApp) + _apps.value
        return newApp
    }

    fun updateApp(
        appId: String,
        name: String,
        tagline: String,
        description: String,
        category: AppCategory
    ) {
        _apps.value = _apps.value.map { app ->
            if (app.id == appId) {
                app.copy(name = name, tagline = tagline, description = description, category = category)
            } else {
                app
            }
        }
    }

    fun deleteApp(appId: String) {
        _apps.value = _apps.value.filter { it.id != appId }
    }

    fun addAppVersion(appId: String, newVersionStr: String, changelog: String, sizeMb: Double) {
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val today = dateFormat.format(Date())
        _apps.value = _apps.value.map { app ->
            if (app.id == appId) {
                val newVerObj = AppVersion(newVersionStr, today, changelog, sizeMb, "https://dl.zainx.io/${app.id}_v${newVersionStr}${app.platform.extension}")
                app.copy(
                    version = newVersionStr,
                    changelog = changelog,
                    fileSizeMb = sizeMb,
                    releaseDate = today,
                    versions = listOf(newVerObj) + app.versions
                )
            } else {
                app
            }
        }
    }

    fun submitReport(appId: String, appName: String, reporterEmail: String, reason: String) {
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val today = dateFormat.format(Date())
        val newReport = ReportItem(
            id = "rep_${System.currentTimeMillis()}",
            appId = appId,
            appName = appName,
            reporterEmail = reporterEmail,
            reason = reason,
            date = today,
            isResolved = false
        )
        _reports.value = listOf(newReport) + _reports.value
    }

    // Admin methods
    fun setAppStatus(appId: String, status: AppStatus) {
        _apps.value = _apps.value.map { app ->
            if (app.id == appId) app.copy(status = status) else app
        }
    }

    fun resolveReport(reportId: String) {
        _reports.value = _reports.value.map { report ->
            if (report.id == reportId) report.copy(isResolved = true) else report
        }
    }

    fun updateSiteSettings(siteName: String, maintenance: Boolean, maxUploadMb: Int) {
        _siteName.value = siteName
        _maintenanceMode.value = maintenance
        _maxUploadSizeMb.value = maxUploadMb
    }

    fun updateUser(name: String, bio: String, location: String, website: String) {
        _currentUser.value = _currentUser.value.copy(
            name = name,
            bio = bio,
            location = location,
            website = website
        )
    }

    fun switchRole(newRole: UserRole) {
        _currentUser.value = _currentUser.value.copy(role = newRole)
    }

    fun login(email: String, name: String, role: UserRole) {
        _currentUser.value = _currentUser.value.copy(
            email = email,
            name = name.ifBlank { email.substringBefore("@") },
            username = email.substringBefore("@"),
            role = role
        )
    }
}
