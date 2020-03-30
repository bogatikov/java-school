package com.narnia.railways.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Station f_node;

    @ManyToOne
    private Station s_node;

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "weight")
    private Long length;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "train_path",
            joinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Train_id", referencedColumnName = "id")
    )
    private List<Train> trains;

    public boolean hasFreeway() {
        return !reserved;
    }

    public void reserveFreeway() {
        this.reserved = true;
    }

    public void freeReservation() {
        this.reserved = false;
    }
}
