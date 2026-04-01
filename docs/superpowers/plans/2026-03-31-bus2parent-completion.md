# Bus2Parent API Completion Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Complete the Bus2Parent school district live tracking REST API by finishing the BusParent CRUD layer, wiring up database configuration, and adding a bus location tracking endpoint.

**Architecture:** The project follows a 3-layer Spring Boot architecture: Controller (REST) → Service (business logic) → DAO (JDBC via NamedParameterJdbcTemplate). All entities follow the same pattern. The incomplete piece is the `BusParent` join table (bus ↔ parent subscriptions) which currently has a NullPointerException bug and missing CRUD methods. The database is MySQL with a hardcoded `SpringJdbcConfig`; configuration needs to move to `application.properties` so credentials aren't hardcoded.

**Tech Stack:** Java 11, Spring Boot 2.3.4, Spring JDBC (NamedParameterJdbcTemplate), MySQL 8, Apache Commons Lang3, Maven

---

## File Map

| File | Action | Purpose |
|------|--------|---------|
| `src/main/resources/application.properties` | Modify | Add datasource config (url, username, password, driver) |
| `src/main/java/.../config/SpringJdbcConfig.java` | Modify | Remove hardcoded credentials; use Spring Boot's auto-configured datasource |
| `src/main/java/.../dao/BusParentDao.java` | Modify | Add createBusParent, getBusParentsByBus, removeBusParent |
| `src/main/java/.../service/BusParentService.java` | Modify | Fix NPE (@Autowired), add service methods |
| `src/main/java/.../v1/controller/BusParentController.java` | Modify | Add POST, DELETE, GET-by-busId endpoints |
| `src/main/java/.../domain/BusLocation.java` | Create | New domain object: busNumber + latitude + longitude |
| `src/main/java/.../dao/BusLocationDao.java` | Create | JDBC CRUD for BUS_LOCATION table |
| `src/main/java/.../dao/BusLocationRowMapper.java` | Create | RowMapper for BusLocation |
| `src/main/java/.../service/BusLocationService.java` | Create | Service layer for location operations |
| `src/main/java/.../v1/controller/BusLocationController.java` | Create | GET /v1/locations/{busNumber}, POST /v1/locations |

---

## Task 1: Fix datasource configuration

The `SpringJdbcConfig.java` hardcodes DB credentials and overrides Spring Boot's auto-configuration. Move everything to `application.properties` so the app can start cleanly without a manual config class.

**Files:**
- Modify: `src/main/resources/application.properties`
- Modify: `src/main/java/com/connect/bus2parent/config/SpringJdbcConfig.java`

- [ ] **Step 1: Update application.properties with datasource config**

Replace the contents of `src/main/resources/application.properties` with:

```properties
spring.application.name=Bus2parent Connect

spring.datasource.url=jdbc:mysql://localhost:3306/BUS2PARENT
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

> Note: Set `spring.datasource.password` to your actual MySQL root password if one is set.

- [ ] **Step 2: Remove SpringJdbcConfig so Spring Boot uses auto-configured datasource**

Replace `src/main/java/com/connect/bus2parent/config/SpringJdbcConfig.java` entirely with:

```java
package com.connect.bus2parent.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringJdbcConfig {
    // Spring Boot auto-configures DataSource and NamedParameterJdbcTemplate
    // from application.properties — no manual bean definition needed.
}
```

- [ ] **Step 3: Build the project to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS with no errors.

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/application.properties src/main/java/com/connect/bus2parent/config/SpringJdbcConfig.java
git commit -m "fix: move datasource config to application.properties"
```

---

## Task 2: Fix BusParentService NullPointerException

The `BusParentService` declares `private BusParentDao busParentDao` but is missing `@Autowired`, so it's always null at runtime.

**Files:**
- Modify: `src/main/java/com/connect/bus2parent/service/BusParentService.java`

- [ ] **Step 1: Add @Autowired to BusParentDao in BusParentService**

Replace `src/main/java/com/connect/bus2parent/service/BusParentService.java` entirely with:

```java
package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.dao.BusParentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusParentService {

    @Autowired
    private BusParentDao busParentDao;

    public List<BusParent> getBusParents() {
        return busParentDao.getBusParents();
    }
}
```

- [ ] **Step 2: Build to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/connect/bus2parent/service/BusParentService.java
git commit -m "fix: add @Autowired to BusParentDao in BusParentService (NPE fix)"
```

---

## Task 3: Complete BusParentDao CRUD methods

The DAO only has `getBusParents()`. The BUS_PARENT table has a composite primary key: `(BusNumber, ParentEmail)`. We need create, get-by-bus, and delete.

**Files:**
- Modify: `src/main/java/com/connect/bus2parent/dao/BusParentDao.java`

- [ ] **Step 1: Add createBusParent, getBusParentsByBus, and removeBusParent to BusParentDao**

Replace `src/main/java/com/connect/bus2parent/dao/BusParentDao.java` entirely with:

```java
package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusParent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BusParentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BusParent> getBusParents() {
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, ParentEmail FROM BUS_PARENT",
            new HashMap<>(),
            new BusParentRowMapper()
        );
    }

    public List<BusParent> getBusParentsByBus(int busNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, ParentEmail FROM BUS_PARENT WHERE BusNumber=:BusNumber",
            params,
            new BusParentRowMapper()
        );
    }

    public int createBusParent(BusParent busParent) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busParent.busID());
        params.put("ParentEmail", busParent.parentEmail());
        return namedParameterJdbcTemplate.update(
            "INSERT INTO BUS_PARENT (BusNumber, ParentEmail) VALUES (:BusNumber, :ParentEmail)",
            params
        );
    }

    public int removeBusParent(int busNumber, String parentEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        params.put("ParentEmail", parentEmail);
        return namedParameterJdbcTemplate.update(
            "DELETE FROM BUS_PARENT WHERE BusNumber=:BusNumber AND ParentEmail=:ParentEmail",
            params
        );
    }
}
```

- [ ] **Step 2: Build to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/connect/bus2parent/dao/BusParentDao.java
git commit -m "feat: add createBusParent, getBusParentsByBus, removeBusParent to BusParentDao"
```

---

## Task 4: Complete BusParentService and BusParentController

Wire the new DAO methods through the service and expose them as REST endpoints.

**Files:**
- Modify: `src/main/java/com/connect/bus2parent/service/BusParentService.java`
- Modify: `src/main/java/com/connect/bus2parent/v1/controller/BusParentController.java`

- [ ] **Step 1: Add service methods for new DAO operations**

Replace `src/main/java/com/connect/bus2parent/service/BusParentService.java` entirely with:

```java
package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.dao.BusParentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusParentService {

    @Autowired
    private BusParentDao busParentDao;

    public List<BusParent> getBusParents() {
        return busParentDao.getBusParents();
    }

    public List<BusParent> getBusParentsByBus(int busNumber) {
        return busParentDao.getBusParentsByBus(busNumber);
    }

    public int createBusParent(BusParent busParent) {
        return busParentDao.createBusParent(busParent);
    }

    public int removeBusParent(int busNumber, String parentEmail) {
        return busParentDao.removeBusParent(busNumber, parentEmail);
    }
}
```

- [ ] **Step 2: Add REST endpoints to BusParentController**

Replace `src/main/java/com/connect/bus2parent/v1/controller/BusParentController.java` entirely with:

```java
package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.service.BusParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusParentController {

    @Autowired
    private BusParentService busParentService;

    @GetMapping("/v1/busparents")
    public ResponseEntity<List<BusParent>> getBusParents() {
        List<BusParent> busParentList = busParentService.getBusParents();
        return new ResponseEntity<>(busParentList, HttpStatus.OK);
    }

    @GetMapping("/v1/busparents/{busNumber}")
    public ResponseEntity<List<BusParent>> getBusParentsByBus(@PathVariable("busNumber") int busNumber) {
        List<BusParent> busParentList = busParentService.getBusParentsByBus(busNumber);
        return new ResponseEntity<>(busParentList, HttpStatus.OK);
    }

    @PostMapping("/v1/busparents")
    public ResponseEntity<BusParent> createBusParent(@RequestBody BusParent busParent) {
        busParentService.createBusParent(busParent);
        return new ResponseEntity<>(busParent, HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/busparents/{busNumber}/{parentEmail}")
    public ResponseEntity<BusParent> removeBusParent(
            @PathVariable("busNumber") int busNumber,
            @PathVariable("parentEmail") String parentEmail) {
        busParentService.removeBusParent(busNumber, parentEmail);
        BusParent removed = new BusParent(busNumber, parentEmail);
        return new ResponseEntity<>(removed, HttpStatus.OK);
    }
}
```

- [ ] **Step 3: Build to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/connect/bus2parent/service/BusParentService.java \
        src/main/java/com/connect/bus2parent/v1/controller/BusParentController.java
git commit -m "feat: complete BusParent CRUD — service and controller endpoints"
```

---

## Task 5: Create BUS_LOCATION MySQL table

The live tracking feature needs a table to store the latest known GPS coordinates for each bus.

**Files:**
- Create: `src/main/resources/schema.sql` (reference only — run manually against MySQL)

- [ ] **Step 1: Create the BUS_LOCATION table in MySQL**

Connect to your local MySQL instance and run:

```sql
USE BUS2PARENT;

CREATE TABLE IF NOT EXISTS BUS_LOCATION (
    BusNumber INT NOT NULL PRIMARY KEY,
    Latitude  DOUBLE NOT NULL,
    Longitude DOUBLE NOT NULL,
    UpdatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bus_location_bus FOREIGN KEY (BusNumber) REFERENCES BUS(BusNumber)
);
```

- [ ] **Step 2: Save schema for reference**

Create `src/main/resources/schema.sql` with:

```sql
-- Run this against the BUS2PARENT MySQL database to create required tables.
-- Existing tables (BUS, DRIVER, PARENT, BUS_PARENT) assumed to already exist.

CREATE TABLE IF NOT EXISTS BUS_LOCATION (
    BusNumber INT NOT NULL PRIMARY KEY,
    Latitude  DOUBLE NOT NULL,
    Longitude DOUBLE NOT NULL,
    UpdatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bus_location_bus FOREIGN KEY (BusNumber) REFERENCES BUS(BusNumber)
);
```

- [ ] **Step 3: Commit schema file**

```bash
git add src/main/resources/schema.sql
git commit -m "docs: add schema.sql with BUS_LOCATION table definition"
```

---

## Task 6: Implement BusLocation domain, RowMapper, and DAO

**Files:**
- Create: `src/main/java/com/connect/bus2parent/domain/BusLocation.java`
- Create: `src/main/java/com/connect/bus2parent/dao/BusLocationRowMapper.java`
- Create: `src/main/java/com/connect/bus2parent/dao/BusLocationDao.java`

- [ ] **Step 1: Create BusLocation domain object**

Create `src/main/java/com/connect/bus2parent/domain/BusLocation.java`:

```java
package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BusLocation {

    @JsonProperty("bus_number")
    private int busNumber;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public BusLocation() { }

    public BusLocation(int busNumber, double latitude, double longitude) {
        this.busNumber = busNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int busNumber() { return busNumber; }

    public BusLocation setBusNumber(int busNumber) {
        this.busNumber = busNumber;
        return this;
    }

    public double latitude() { return latitude; }

    public BusLocation setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double longitude() { return longitude; }

    public BusLocation setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusLocation that = (BusLocation) o;
        return new EqualsBuilder()
            .append(busNumber, that.busNumber)
            .append(latitude, that.latitude)
            .append(longitude, that.longitude)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(busNumber).append(latitude).append(longitude).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("busNumber", busNumber)
            .append("latitude", latitude)
            .append("longitude", longitude)
            .toString();
    }
}
```

- [ ] **Step 2: Create BusLocationRowMapper**

Create `src/main/java/com/connect/bus2parent/dao/BusLocationRowMapper.java`:

```java
package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusLocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusLocationRowMapper implements RowMapper<BusLocation> {

    @Override
    public BusLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        BusLocation loc = new BusLocation();
        loc.setBusNumber(rs.getInt("BusNumber"));
        loc.setLatitude(rs.getDouble("Latitude"));
        loc.setLongitude(rs.getDouble("Longitude"));
        return loc;
    }
}
```

- [ ] **Step 3: Create BusLocationDao**

Create `src/main/java/com/connect/bus2parent/dao/BusLocationDao.java`:

```java
package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BusLocationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BusLocation> getAllLocations() {
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, Latitude, Longitude FROM BUS_LOCATION",
            new HashMap<>(),
            new BusLocationRowMapper()
        );
    }

    public BusLocation getLocation(int busNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        return namedParameterJdbcTemplate.queryForObject(
            "SELECT BusNumber, Latitude, Longitude FROM BUS_LOCATION WHERE BusNumber=:BusNumber",
            params,
            new BusLocationRowMapper()
        );
    }

    public int upsertLocation(BusLocation location) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", location.busNumber());
        params.put("Latitude", location.latitude());
        params.put("Longitude", location.longitude());
        return namedParameterJdbcTemplate.update(
            "INSERT INTO BUS_LOCATION (BusNumber, Latitude, Longitude) VALUES (:BusNumber, :Latitude, :Longitude) " +
            "ON DUPLICATE KEY UPDATE Latitude=:Latitude, Longitude=:Longitude",
            params
        );
    }
}
```

- [ ] **Step 4: Build to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/connect/bus2parent/domain/BusLocation.java \
        src/main/java/com/connect/bus2parent/dao/BusLocationRowMapper.java \
        src/main/java/com/connect/bus2parent/dao/BusLocationDao.java
git commit -m "feat: add BusLocation domain, RowMapper, and DAO"
```

---

## Task 7: Implement BusLocationService and BusLocationController

**Files:**
- Create: `src/main/java/com/connect/bus2parent/service/BusLocationService.java`
- Create: `src/main/java/com/connect/bus2parent/v1/controller/BusLocationController.java`

- [ ] **Step 1: Create BusLocationService**

Create `src/main/java/com/connect/bus2parent/service/BusLocationService.java`:

```java
package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.BusLocationDao;
import com.connect.bus2parent.domain.BusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusLocationService {

    @Autowired
    private BusLocationDao busLocationDao;

    public List<BusLocation> getAllLocations() {
        return busLocationDao.getAllLocations();
    }

    public BusLocation getLocation(int busNumber) {
        return busLocationDao.getLocation(busNumber);
    }

    public int updateLocation(BusLocation location) {
        return busLocationDao.upsertLocation(location);
    }
}
```

- [ ] **Step 2: Create BusLocationController**

Create `src/main/java/com/connect/bus2parent/v1/controller/BusLocationController.java`:

```java
package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.BusLocation;
import com.connect.bus2parent.service.BusLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusLocationController {

    @Autowired
    private BusLocationService busLocationService;

    @GetMapping("/v1/locations")
    public ResponseEntity<List<BusLocation>> getAllLocations() {
        List<BusLocation> locations = busLocationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/v1/locations/{busNumber}")
    public ResponseEntity<BusLocation> getLocation(@PathVariable("busNumber") int busNumber) {
        BusLocation location = busLocationService.getLocation(busNumber);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping("/v1/locations")
    public ResponseEntity<BusLocation> updateLocation(@RequestBody BusLocation location) {
        busLocationService.updateLocation(location);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
```

- [ ] **Step 3: Build and run the full application**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

Start the app (requires MySQL running with BUS2PARENT database):

```bash
./mvnw spring-boot:run
```

Expected: Application starts on port 8080 with no errors.

- [ ] **Step 4: Smoke test the BusParent endpoints (requires running app + data)**

```bash
# Subscribe a parent to a bus
curl -s -X POST http://localhost:8080/v1/busparents \
  -H "Content-Type: application/json" \
  -d '{"bus_id": 1, "parent_email": "parent@example.com"}'

# Get all parents for bus 1
curl -s http://localhost:8080/v1/busparents/1

# Update bus 1 location
curl -s -X POST http://localhost:8080/v1/locations \
  -H "Content-Type: application/json" \
  -d '{"bus_number": 1, "latitude": 37.7749, "longitude": -122.4194}'

# Get bus 1 location
curl -s http://localhost:8080/v1/locations/1
```

Expected: Each curl returns HTTP 2xx with JSON body.

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/connect/bus2parent/service/BusLocationService.java \
        src/main/java/com/connect/bus2parent/v1/controller/BusLocationController.java
git commit -m "feat: add BusLocationService and BusLocationController (live tracking endpoints)"
```

---

## Task 8: Add @JsonProperty annotations to BusParent domain

`BusParent` fields don't have `@JsonProperty` annotations, so JSON serialization uses field names directly (`busID`, `parentEmail`) rather than the snake_case convention used by all other domain objects (`bus_id`, `parent_email`).

**Files:**
- Modify: `src/main/java/com/connect/bus2parent/domain/BusParent.java`

- [ ] **Step 1: Add @JsonProperty annotations to BusParent**

Replace `src/main/java/com/connect/bus2parent/domain/BusParent.java` entirely with:

```java
package com.connect.bus2parent.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BusParent {

    @JsonProperty("bus_id")
    int busID;

    @JsonProperty("parent_email")
    String parentEmail;

    public BusParent() { }

    public BusParent(int bID, String pEmail) {
        busID = bID;
        parentEmail = pEmail;
    }

    public int busID() {
        return busID;
    }

    public BusParent setBusID(int busID) {
        this.busID = busID;
        return this;
    }

    public String parentEmail() {
        return parentEmail;
    }

    public BusParent setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusParent busParent = (BusParent) o;
        return new EqualsBuilder()
            .append(busID, busParent.busID)
            .append(parentEmail, busParent.parentEmail)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(busID).append(parentEmail).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("busID", busID)
            .append("parentEmail", parentEmail)
            .toString();
    }
}
```

- [ ] **Step 2: Build to confirm no compilation errors**

```bash
./mvnw compile -q
```

Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/connect/bus2parent/domain/BusParent.java
git commit -m "fix: add @JsonProperty snake_case annotations to BusParent domain"
```

---

## Summary of All Endpoints After Completion

| Method | Path | Description |
|--------|------|-------------|
| GET | `/v1/buses` | All buses |
| GET | `/v1/buses/{busNumber}` | Bus by number |
| POST | `/v1/buses` | Create bus |
| POST | `/v1/buses/batch` | Create multiple buses |
| POST | `/v1/buses/register` | Register bus (auto-assign number) |
| DELETE | `/v1/buses/{busNumber}` | Remove bus |
| PUT | `/v1/buses/{busNumber}` | Update bus plate |
| GET | `/v1/drivers` | All drivers |
| GET | `/v1/drivers/{id}` | Driver by ID |
| POST | `/v1/drivers` | Create driver |
| POST | `/v1/drivers/batch` | Create multiple drivers |
| POST | `/v1/drivers/register` | Register driver (auto-assign ID) |
| DELETE | `/v1/drivers/{id}` | Remove driver |
| PUT | `/v1/drivers/{id}` | Update driver |
| GET | `/v1/parents` | All parents |
| GET | `/v1/parents/{emailAddress}` | Parent by email |
| POST | `/v1/parents` | Create parent |
| POST | `/v1/parents/batch` | Create multiple parents |
| DELETE | `/v1/parents/{email}` | Remove parent |
| PUT | `/v1/parents/{email}` | Update parent |
| GET | `/v1/busparents` | All bus-parent subscriptions |
| GET | `/v1/busparents/{busNumber}` | Parents subscribed to a bus |
| POST | `/v1/busparents` | Subscribe parent to bus |
| DELETE | `/v1/busparents/{busNumber}/{parentEmail}` | Unsubscribe parent from bus |
| GET | `/v1/locations` | All bus locations |
| GET | `/v1/locations/{busNumber}` | Location for a specific bus |
| POST | `/v1/locations` | Update bus location (upsert) |
