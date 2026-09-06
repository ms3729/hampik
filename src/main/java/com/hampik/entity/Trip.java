package com.hampik.entity;

import com.hampik.enums.TripStatus;
import com.hampik.enums.TripType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripType type;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status = TripStatus.active;

    @Column(nullable = false)
    private LocalDate startDate;

    private OffsetDateTime finishedAt;

    // Fields specific to trip type
    private String origin;
    private String destination;
    private String routeName;
    private int totalKm;

    // Fields specific to gathering/outing/amusement types
    private String location_type;
    private String tim;
}
