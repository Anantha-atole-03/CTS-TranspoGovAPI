
INSERT INTO routes
(route_id, start_point, end_point, title, type, status)
VALUES
(1, 'Chennai', 'Bangalore', 'Chennai–Bangalore Express', 'INTERCITY', 'ACTIVE'),
(2, 'Chennai', 'Coimbatore', 'Chennai–Coimbatore Superfast', 'INTERCITY', 'ACTIVE'),
(3, 'Madurai', 'Trichy', 'Madcitizenscitizensurai–Trichy Local', 'INTRACITY', 'DRAFT'),
(4, 'Salem', 'Erode', 'Salem–Erode Shuttle', 'INTRACITY', 'INACTIVE');
select * from routes;
select * from tickets;

/* =========================
   USERS
========================= */
INSERT INTO users (user_id, name, role, email, phone, password, status) VALUES
(1, 'Admin User', 'ADMINISTRATOR', 'admin@transpo.gov', '9000000001', 'pass123', 'ACTIVE'),
(2, 'Transport Officer', 'TRANSPORT_OFFICER', 'officer@transpo.gov', '9000000002', 'pass123', 'ACTIVE'),
(3, 'Program Manager', 'PROGRAM_MANAGER', 'manager@transpo.gov', '9000000003', 'pass123', 'ACTIVE'),
(4, 'Compliance Officer', 'COMPLIANCE_OFFICER', 'compliance@transpo.gov', '9000000004', 'pass123', 'ACTIVE'),
(5, 'Auditor', 'GOVERNMENT_AUDITOR', 'auditor@transpo.gov', '9000000005', 'pass123', 'ACTIVE');


/* =========================
   CITIZENS
========================= */
INSERT INTO citizens (citizen_id, name, dob, gender, address, contact_info, email, password, status) VALUES
(1, 'Ravi Kumar', '1995-05-10', 'MALE', 'Chennai', '9001111111', 'ravi@mail.com', 'pass', 'VERIFIED'),
(2, 'Anita Sharma', '1998-09-12', 'FEMALE', 'Mumbai', '9002222222', 'anita@mail.com', 'pass', 'ACTIVE'),
(3, 'Rahul Verma', '1990-01-15', 'MALE', 'Delhi', '9003333333', 'rahul@mail.com', 'pass', 'ACTIVE'),
(4, 'Priya Singh', '1997-07-20', 'FEMALE', 'Bangalore', '9004444444', 'priya@mail.com', 'pass', 'PENDING_VERIFICATION'),
(5, 'Suresh Patel', '1985-11-25', 'MALE', 'Ahmedabad', '9005555555', 'suresh@mail.com', 'pass', 'ACTIVE');


/* =========================
   ROUTES
========================= */
INSERT INTO routes (route_id, title, type, start_point, end_point, status) VALUES
(1, 'Route A', 'BUS', 'Central Station', 'Airport', 'ACTIVE'),
(2, 'Route B', 'BUS', 'City Center', 'IT Park', 'ACTIVE'),
(3, 'Route C', 'METRO', 'North Station', 'South Station', 'INACTIVE'),
(4, 'Route D', 'BUS', 'Old Town', 'New Town', 'DRAFT'),
(5, 'Route E', 'TRAM', 'Museum', 'Harbor', 'ACTIVE');


/* =========================
   SCHEDULES
========================= */
INSERT INTO schedules (schedule_id, route_id, date, time, status) VALUES
(1, 1, '2026-04-01', '08:30:00', 'SCHEDULED'),
(2, 1, '2026-04-01', '18:30:00', 'SCHEDULED'),
(3, 2, '2026-04-02', '09:00:00', 'COMPLETED'),
(4, 3, '2026-04-03', '10:00:00', 'CANCELLED'),
(5, 4, '2026-04-04', '07:30:00', 'SCHEDULED');


/* =========================
   TRANSPORT PROGRAMS
========================= */
INSERT INTO transport_programs (program_id, title, description, start_date, end_date, budget, status) VALUES
(1, 'Metro Expansion', 'Metro line extension project', '2026-01-01', '2027-01-01', 5000000, 'IN_PROGRESS'),
(2, 'Electric Buses', 'Deployment of EV buses', '2026-02-01', '2026-12-31', 3000000, 'APPROVED'),
(3, 'Smart Ticketing', 'NFC based ticketing', '2026-03-01', '2026-10-01', 1500000, 'DRAFT'),
(4, 'Green Routes', 'Eco friendly routes', '2026-04-01', '2027-03-01', 2000000, 'SUBMITTED'),
(5, 'Rural Connectivity', 'Village transport', '2026-01-15', '2027-06-01', 3500000, 'IN_PROGRESS');


/* =========================
   RESOURCES
========================= */
INSERT INTO resources (resource_id, program_id, type, quantity, budget, status) VALUES
(1, 1, 'VEHICLES', 20, 2000000, 'IN_USE'),
(2, 1, 'FUNDS', 1, 3000000, 'ASSIGNED'),
(3, 2, 'VEHICLES', 50, 2500000, 'ASSIGNED'),
(4, 3, 'FUNDS', 1, 1500000, 'AVAILABLE'),
(5, 4, 'VEHICLES', 10, 1200000, 'IN_PROCUREMENT');


/* =========================
   TICKETS
========================= */
INSERT INTO tickets (ticket_id, citizen_id, route_id, date, fare_amount, status) VALUES
(1, 1, 1, '2026-03-20 09:00:00', 50.0, 'CONFIRMED'),
(2, 2, 2, '2026-03-21 10:00:00', 40.0, 'CONFIRMED'),
(3, 3, 1, '2026-03-21 11:00:00', 60.0, 'CANCELLED'),
(4, 4, 3, '2026-03-22 12:00:00', 30.0, 'PENDING_PAYMENT'),
(5, 5, 5, '2026-03-23 13:00:00', 70.0, 'EXPIRED');


/* =========================
   PAYMENTS
========================= */
desc payments;
INSERT INTO payments (payment_id, ticket_id, method, date, status) VALUES
(1, 1, 'UPI', '2026-03-20 09:05:00', 'SUCCESS'),
(2, 2, 'CARD', '2026-03-21 10:05:00', 'SUCCESS'),
(3, 3, 'CASH', '2026-03-21 11:05:00', 'FAILED'),
(4, 4, 'UPI', '2026-03-22 12:05:00', 'PENDING'),
(5, 5, 'CARD', '2026-03-23 13:05:00', 'REFUNDED');


/* =========================
   AUDIT
========================= */
desc audit;
INSERT INTO audit (Auditid, officer_id, Scope, Status, Date, closed_at, findings) VALUES
(1, 2, 'Route Compliance', 'OPEN', NOW(), NULL, 'Initial audit'),
(2, 2, 'Ticketing', 'IN_PROGRESS', NOW(), NULL, 'Checking fare issues'),
(3, 4, 'Program Audit', 'CLOSED', NOW(), NOW(), 'No issues'),
(4, 5, 'Resource Audit', 'OPEN', NOW(), NULL, 'Pending review'),
(5, 4, 'Safety Audit', 'DELETED', NOW(), NULL, 'Cancelled');


/* =========================
   COMPLIANCE RECORD
========================= */
desc compliance_records;
INSERT INTO compliance_records (ComplianceID, Date, EntityID, Notes, Result, Type) VALUES
(1, '2026-03-01', 1, 'All standards met', 'COMPLIANT', 'ROUTE'),
(2, '2026-03-02', 2, 'Minor issues', 'OBSERVATION', 'TICKET'),
(3, '2026-03-03', 3, 'Major violation', 'NON_COMPLIANT', 'PROGRAM'),
(4, '2026-03-04', 4, 'Audit failed', 'FAIL', 'ROUTE'),
(5, '2026-03-05', 5, 'Not applicable', 'NA', 'PROGRAM');


/* =========================
   NOTIFICATIONS
========================= */
INSERT INTO notifications (notification_id, user_id, citizen_id, entity_id, message, category, scope, status) VALUES
(1, 1, NULL, 1, 'Program approved', 'PROGRAM', 'USER', 'UNREAD'),
(2, NULL, 1, 1, 'Ticket confirmed', 'TICKET', 'CITIZEN', 'UNREAD'),
(3, NULL, 2, 2, 'Payment successful', 'TICKET', 'CITIZEN', 'READ'),
(4, 3, NULL, 3, 'Audit assigned', 'COMPLIANCE', 'USER', 'UNREAD'),
(5, NULL, NULL, NULL, 'System maintenance', 'USER', 'GLOBAL', 'UNREAD');