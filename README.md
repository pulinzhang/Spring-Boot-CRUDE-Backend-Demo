# Spring Boot CRUDE Backend Demo

A professional Java Spring Boot backend demonstrating full CRUDE operations, advanced search with pagination, and JasperReports integration.

---

## Features Implemented

### ✅ CRUDE Operations
- **Create** - Single and batch record creation
- **Read** - Query single/multiple records with advanced filtering
- **Update** - Single and batch updates
- **Delete** - Delete by composite primary keys

### ✅ Advanced Search
- Multi-field filtering
- Pagination (configurable page size)
- Sort by any field (ASC/DESC)
- Dynamic query building using JPA Specifications

### ✅ JasperReports Integration
- PDF report generation from database
- Customizable report templates
- REST endpoint for export

### ✅ Database Support
- H2 (in-memory for demo)
- MySQL (production-ready)

---

## Demo Example: NotificationLog Table

This demo implements a complete CRUDE for `notificationlog` table with:

- **Composite Primary Key**: (userId, createtime)
- **24 Fields**: Including title, body, platform, status, device token, FCM message ID, etc.
- **Auditing**: Automatic created/updated timestamps

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v3/notificationlog/list` | Search with pagination & filters |
| POST | `/api/v3/notificationlog/byid` | Get records by IDs |
| GET | `/api/v3/notificationlog/findall` | Get all records |
| POST | `/api/v3/notificationlog` | Create new record |
| PUT | `/api/v3/notificationlog` | Update records |
| DELETE | `/api/v3/notificationlog` | Delete by IDs |
| GET | `/api/v3/report/notificationlog/pdf` | Export to PDF |

---

## JSON Request Examples

### Search with Pagination

```json
POST /api/v3/notificationlog/list
{
  "page": 0,
  "size": 10,
  "searchedfields": [
    {
      "fieldName": "userId",
      "values": ["1", "2"]
    }
  ],
  "sortbyfields": [
    {
      "fieldName": "createtime",
      "direction": "DESC"
    }
  ]
}
```

### Create Record

```json
POST /api/v3/notificationlog
{
  "userId": 1,
  "title": "Hello World",
  "body": "Notification message",
  "platform": "ANDROID",
  "status": "PENDING",
  "creator": 1
}
```

### Update Records

```json
PUT /api/v3/notificationlog
[
  {
    "userId": 1,
    "createtime": "2026-03-01T10:30:00",
    "title": "Updated Title",
    "status": "SENT",
    "modifier": 2
  }
]
```

### Delete by IDs

```json
DELETE /api/v3/notificationlog
[
  {
    "userId": 1,
    "createtime": "2026-03-01T10:30:00"
  }
]
```

### Export PDF Report

```
GET /api/v3/report/notificationlog/pdf
```

---

## Postman Test Screenshots

Below are the Postman test screenshots for each API endpoint:

| Test | Description | Screenshot |
|------|-------------|------------|
| 01 | Search with Pagination | `docs/postman/01_list.png` |
| 02 | Get by Composite ID | `docs/postman/02_byid.png` |
| 03 | Get All Records | `docs/postman/03_findall.png` |
| 04 | Create Record | `docs/postman/04_create.png` |
| 05 | Update Records (Batch) | `docs/postman/05_update.png` |
| 06 | Delete Records (Batch) | `docs/postman/06_delete.png` |
| 07 | Export PDF Report | `docs/postman/07_pdf_export.png` |

---

## Database Schema

### Part1: Identify the Table on the DB with Primary Keys

Before coding, analyze the table structure to identify primary keys and fields:

![Database Schema](docs/postman/00-db-schema.png)

---

## Project Structure

```
src/main/java/com/example/cruddemo/
├── CrudDemoApplication.java           # Main application
├── dto/                               # Request/Response DTOs
│   ├── SearchRequestDto.java         # Search request with pagination
│   ├── SearchedBy.java                # Filter field definition
│   └── SortBy.java                    # Sort configuration
├── enums/                             # Enumerations
│   ├── DevicePlatform.java            # ANDROID, IOS, WEB
│   └── NotificationStatus.java        # PENDING, SENT, FAILED
├── fcm/                               # NotificationLog module
│   ├── NotificationLog.java           # Entity (24 fields)
│   ├── NotificationlogPK.java        # Composite Primary Key
│   ├── NotificationLogRepository.java
│   ├── NotificationLogService.java
│   └── NotificationLogController.java
├── report/                            # JasperReports
│   ├── ReportController.java          # PDF export endpoint
│   ├── ReportService.java             # Report generation
│   └── notificationlog_report.jrxml   # Report template
└── repository/
    └── SearchRepository.java          # Dynamic query builder
```

---

## Technology Stack

| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 4.0.3 |
| Spring Data JPA | - |
| Hibernate | - |
| JasperReports | 6.20.0 |
| H2 Database | - |
| MySQL | 8.x |
| Lombok | - |

---

## How to Run

```bash
# Clone and run
mvn spring-boot:run
```

Server: `http://localhost:8081`

---

## Can Deliver

| Service | Description |
|---------|-------------|
| CRUDE per table | Full CRUDE with composite keys, ~24 fields average |
| JasperReport | PDF export with custom template |
| Search/Filter | Advanced multi-field search with pagination |
| Database | MySQL, PostgreSQL, or H2 |

---

## Contact

Available for freelance work. Can deliver 1+ table per day.

- Long-term projects welcome
- Flexible hours (1+ hours/day)
- 100 tables + 50 reports capability

---

*Demo project for portfolio purposes. Full customization available.*
