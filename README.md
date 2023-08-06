# Data Collector App - README

![Data Collector App Logo](https://mathiasfrohlich.gallerycdn.vsassets.io/extensions/mathiasfrohlich/kotlin/1.7.1/1581441165235/Microsoft.VisualStudio.Services.Icons.Default)

## Overview

The Data Collector app is designed to facilitate data collection in various municipalities of the state. It allows users to gather information about families in different phases, offering offline-first functionality with offline data persistence. The app enables generating partial or final reports in PDF format based on the collected data, including photographs, beneficiary information, signatures, timestamps, and geolocation. Additionally, the app supports creating projects with details about professionals, project scope, and programs to be executed.

## Features

### Data Collection and Reporting

- Collect data from families in different municipalities.
- Record data in three phases of data collection.
- Store photographs and associate them with reports.
- Capture personal details of beneficiaries.
- Enable professionals and supervisors to sign each report.
- Timestamp and geolocate each collected data entry.
- Record the professional who completed the data collection.

### Report Generation

- Generate partial or final reports in PDF format.
- Utilize a template for report generation.
- Include photographs and saved data in the reports.
- Incorporate personal details of beneficiaries and professionals.
- Provide options to include multiple programs in a single report.

### Project Creation

- Create projects with relevant information, such as:
  - Related professionals.
  - Project scope or the number of beneficiaries.
  - Executed programs (family, young, woman) in specific municipalities.

### User Management

- Create and edit users with roles as professionals or supervisors.
- Gather personal and contact information for users, including:
  - Document number.
  - Full name.
  - Date of birth.
  - Residential address.
  - Email address.
  - Professional title.

### Statistical Visualization

- Display statistics related to contracts, for example:
  - Number of families with disabled members.
  - Number of families in extreme poverty.
  - Number of reported cases of domestic violence.
  - Number of students benefiting from displacement, victims, or belonging to ethnic groups.
- Organize statistics by program, with specific indicators for each program.

## Technologies Used

The Data Collector app is built using a variety of modern technologies and frameworks to ensure a robust and efficient user experience. The key technologies used in the app include:

Kotlin:  The primary programming language used for Android app development, known for its conciseness and safety features.
Jetpack Compose: The modern UI toolkit from Google, which enables a declarative and efficient way to build UI components.
MVVM Architecture: The Model-View-ViewModel architectural pattern, separating data logic, UI representation, and user interactions.
Dagger Hilt 2: A dependency injection framework for managing app dependencies and promoting code modularity.
Firebase Database: A real-time NoSQL database from Google's Firebase platform, offering seamless data synchronization and offline capabilities.
Room Database: A local persistence library from Android Jetpack, providing an SQLite abstraction layer for efficient data storage and retrieval.
Google Maps API: Integration of Google Maps services to display and interact with geolocation data.
These technologies are used in combination to create a powerful, user-friendly, and efficient data collection app with offline-first capabilities. By leveraging these modern tools, the Data Collector app aims to deliver a seamless and productive experience for its users.

## Usage

Provide instructions on how to use the app, including data collection, report generation, project creation, and user management.

## Getting Started

### Prerequisites

List any prerequisites or dependencies required to run the Data Collector app.

### Installation

Provide step-by-step instructions on how to install the app on different platforms.

### Configuration

Explain any configuration settings required for the app to function correctly.

## Contributing

If you want to contribute to the Data Collector app, follow the guidelines and code of conduct outlined in CONTRIBUTING.md.

## License

Specify the license under which the Data Collector app is distributed.

## Contact

If you have any questions or feedback, contact me at juanieltupa@gmail.com.

## Acknowledgments

- Acknowledge any third-party libraries or resources used in the app.
