package com.poly.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSoldBooks implements Serializable {
    @Id
    String tenSach;
    String danhMuc;
    Long soLuongBan;
    Date ngayCu;
    Date ngayMoi;
}


