package com.hampik.entity;

import com.hampik.enums.TripType;
import com.hampik.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripType type;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TripStatus status = TripStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDate startDate;

    private OffsetDateTime finishedAt;

    // Fields specific to trip type
    private String from;
    private String to;
    private String routeName;
    private Double totalKm;

    // Fields specific to gathering/outing/amusement types
    private String location;
    private String time;
}
