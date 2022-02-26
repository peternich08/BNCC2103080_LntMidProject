package Models;

public class Cloth extends Produk {
  private String size;

  public Cloth(String name, int harga, String size) {
    this.namaProduk = name;
    this.hargaProduk = harga;
    this.size = size;
  }

  public Cloth() {}

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }
}
