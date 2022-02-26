package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Food extends Produk {
  private LocalDateTime expireDate = null;

  public Food(String name, int harga, int date, int month, int year) {
    this.namaProduk = name;
    this.hargaProduk = harga;
    setExpireDate(date, month, year);
  }

  public Food() {}

  public String getExpireDate() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy");
    return this.expireDate.format(format);
  }

  public void setExpireDate(int date, int month, int year) {
    this.expireDate = LocalDateTime.of(year, month, date, 0, 0);
  }
}
