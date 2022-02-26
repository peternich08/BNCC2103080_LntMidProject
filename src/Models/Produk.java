package Models;

public class Produk {
  protected String namaProduk;
  protected int hargaProduk;

  public String getNamaProduk() {
    return namaProduk;
  }

  public void setNamaProduk(String namaProduk) {
    this.namaProduk = namaProduk;
  }

  public int getHargaProduk() {
    return hargaProduk;
  }

  public void setHargaProduk(int hargaProduk) {
    this.hargaProduk = hargaProduk;
  }
}