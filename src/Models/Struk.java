package Models;

import java.util.ArrayList;

public class Struk {
  public static int id = 1;
  private int strukId;
  private ArrayList<Produk> produkList = null;
  private int totalHarga;

  public Struk(ArrayList<Produk> produkList, int totalHarga) {
    this.strukId = id;
    this.produkList = new ArrayList<>(produkList);
    this.totalHarga = totalHarga;
    id++;
  }

  public void cetakStruk() {
    System.out.println("Padie Shop");
    System.out.println("-------------------------------------");
    System.out.printf("ID: %d\n\n",this.strukId);
    int number = 1;
    for (Produk produk : this.produkList) {
      if (produk instanceof Food) {
        Food temp = (Food)produk;
        System.out.printf(
          "%d. %s - %d\n   - Expire date : %s\n\n",
          number,
          temp.getNamaProduk(),
          temp.getHargaProduk(),
          temp.getExpireDate()
        );
      } else if (produk instanceof Cloth) {
        Cloth temp = (Cloth)produk;
        System.out.printf(
          "%d. %s - %d\n   - Size : %s\n\n",
          number,
          temp.getNamaProduk(),
          temp.getHargaProduk(),
          temp.getSize()
        );
      } else if (produk instanceof Tech) {
        Tech temp = (Tech)produk;
        System.out.printf(
          "%d. %s - %d\n   - Version : %s\n\n",
          number,
          temp.getNamaProduk(),
          temp.getHargaProduk(),
          temp.getVersion()
        );
      }
      number++;
    }
    System.out.println("-------------------------------------");
    System.out.printf("Quantity    : %d\n", this.produkList.size());
    System.out.printf("Total Price : Rp %d\n", this.totalHarga);
    System.out.println("-------------------------------------");
  }

  public int getStrukId() {
    return this.strukId;
  }
}
