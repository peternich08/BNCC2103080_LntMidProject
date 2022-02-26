package Models;

public class Tech extends Produk {
  private String version;

  public Tech(String name, int harga, String version) {
    this.namaProduk = name;
    this.hargaProduk = harga;
    this.version = version;
  }

  public Tech() {}

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
