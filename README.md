Kaushalya Karnataka (ಕೌಶಲ್ಯ ಕರ್ನಾಟಕ)
Kaushalya Karnataka is a mobile-first "Digital Directory" and service marketplace designed to bridge the gap between local skilled professionals (plumbers, electricians, tailors, cooks, etc.) and users across Karnataka.
The application focuses on accessibility, speed, and local relevance, providing a stylized platform for daily-wage workers and small-scale experts to showcase their services.

🚀 Key Features
Service Discovery: A reactive search system that allows users to find professionals by name or category (Plumber, Electrician, etc.) in real-time.
Dual-Language Support: Fully localized in English and Kannada, ensuring accessibility for users across the state with a one-tap language toggle.
Professional Profiles: Detailed worker profiles featuring:
Trust Indicators: Verified badges and professional initials.
Stats Grid: Real-time display of ratings, review counts, and total jobs completed.
Service Catalog: A list of specific services offered with transparent starting prices.
Review Wall: A collection of user feedback and star ratings.
One-Tap Hiring: Integrated "Hire Me" action that launches the system dialer with the professional's contact number.
Expert Registration: A streamlined onboarding form for new professionals to join the directory.

🎨 Design Philosophy
Material Design 3: High-contrast UI utilizing a Vibrant Orange (#FF6B00) primary brand color for high visibility.
Accessibility: Large touch targets (32dp rounded corners) and specific support for Kannada Unicode typography.
Mobile-First: Optimized for performance on various Android screen sizes using Jetpack Compose's adaptive layouts.

🛠 Technical Stack
Language: Kotlin
UI Framework: Jetpack Compose (Material 3)
Navigation: Compose Navigation Component
Architecture: MVVM (ViewModel, StateFlow)
Image Loading: Coil
Icons: Material Symbols & Extended Icons

📂 Project Structure
data/: Data models for Workers, Services, and Reviews.
ui/home/: Discovery screen with search and category filters.
ui/profile/: Detailed professional service view.
ui/viewmodel/: Centralized state management for data and localization.
ui/theme/: Custom Material 3 color schemes and typography.
