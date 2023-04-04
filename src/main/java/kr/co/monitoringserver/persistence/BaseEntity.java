package kr.co.monitoringserver.persistence;

import java.time.LocalDate;

public class BaseEntity {

    protected Long id;

    protected LocalDate createdDate;

    protected LocalDate modifiedDate;
}
