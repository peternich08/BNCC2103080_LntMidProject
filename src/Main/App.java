package Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.*;

public class App {
  private Scanner in = new Scanner(System.in);
  public static HashMap<String,User> userList = new HashMap<String,User>();
  public static ArrayList<Produk> produkList = new ArrayList<Produk>();
  private User currentUser = null;

  private void beliProdukMenu() {
    System.out.println("Beli Produk");
    System.out.println("===============");
    System.out.println("1. Pilih produk");
    System.out.println("2. Checkout");
    System.out.println("3. Kembali");
    System.out.print(">> ");
  }

  private void pilihProduk() {
    while (true) {
      int number = 1;
      clearScr();
      System.out.println("Pilih produk");
      System.out.println("============\n");
      for (Produk produk : produkList) {
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

      String input = null;
      int index;

      while (true) {
        System.out.print("Pilih nomor produk ('e' untuk exit) : ");
        input = in.nextLine();
        try {
          index = Integer.parseInt(input);
          if (index > produkList.size() || index <= 0) {
            sendMessage("Nomor tidak ada...");
            continue;
          }
          break;
        } catch (NumberFormatException e) {
          if (input.equals("e")) return;
          sendMessage("Invalid input...");
        }
      }

      currentUser.addToCart(produkList.get(index-1));
      produkList.remove(index-1);
    }
  }

  private void checkOut() {
    if (currentUser.emptyCart()) {
      sendMessage("Keranjang belanja masih kosong");
      return;
    }
    clearScr();
    int total = currentUser.getTotalCart(), saldo = currentUser.getBalance();

    System.out.println("Shoping cart");
    System.out.println("============\n");
    currentUser.showCart();
    System.out.printf("Total : %d\n", total);
    System.out.printf("Saldo anda : %d\n\n", saldo);

    while (true) {
      System.out.println("-------------------------------------");
      System.out.println("1. Bayar");
      System.out.println("2. Reset keranjang");
      System.out.println("3. Kembali ke menu utama");
      System.out.println("-------------------------------------");
      System.out.print("Input : ");
      int option = in.nextInt();
      in.nextLine();

      switch (option) {
        case 1:
          if (saldo < total) {
            sendMessage("Saldo anda tidak mencukupi...");
            return;
          } else {
            clearScr();
            currentUser.checkOut(total);
            freezePrompt();
            return;
          }
        case 2:
          for (Produk prod : currentUser.getShopingCart()) {
            produkList.add(prod);
          }
          currentUser.clearShopingCart();
        case 3:
          return;
        default:
          sendMessage("Input tidak valid!!!");
      }
    }
  }

  private void beliProduk() {
    boolean exit = false;
    while (!exit) {
      clearScr();
      beliProdukMenu();
      int option = in.nextInt();
      in.nextLine();
      switch (option) {
        case 1:
          pilihProduk();
          break;
        case 2:
          checkOut();
          break;
        case 3:
          exit = true;
          break;
        default:
          sendMessage("Invalid input");
          break;
      }
    }
  }

  private void mainMenu() {
    System.out.println("Main Menu");
    System.out.println("=========");
    System.out.println("1. Beli produk");
    System.out.println("2. History pembelian");
    System.out.println("3. Tambah uang");
    System.out.println("4. Cek uang");
    System.out.println("5. Logout");
    System.out.print(">> ");
  }

  private void historyPembelian() {
    String input = null;
    int id = 0;
    while (true) {
      clearScr();
      System.out.println("History pembelian");
      System.out.println("=================");
      currentUser.printStrukId();
      System.out.print("\nMasukan Struk ID yang ingin anda lihat ('e' untuk exit) : ");
      input = in.nextLine();
      try {
        id = Integer.parseInt(input);
        if (!currentUser.strukIdExist(id)) {
          sendMessage("Id tidak ditemukan...");
          continue;
        }
        clearScr();
        currentUser.getStruk(id).cetakStruk();
        freezePrompt();
      } catch (NumberFormatException e) {
        if (input.equals("e")) return;
        sendMessage("Invalid input...");
      }
    }
  }

  private void tambahUang() {
    clearScr();
    int nominal;
    while (true) {
      clearScr();
      System.out.println("Tambah uang");
      System.out.println("===========");
      System.out.print("Masukan nominal uang (0 untuk keluar) : ");
      nominal = in.nextInt();
      in.nextLine();
      if (nominal == 0) return;
      else if (nominal < 0) sendMessage("Nominal uang tidak valid!!!");
      else {
        currentUser.addBalance(nominal);
        sendMessage("Dana berhasil ditambahkan...");
      }
    }
  }

  private void cekUang() {
    clearScr();
    System.out.println("Cek uang");
    System.out.println("========");
    System.out.println("Saldo anda : " + currentUser.getBalance());
    freezePrompt();
  }

  private void mainApp() {
    boolean exit = false;
    while (!exit) {
      clearScr();
      mainMenu();
      int option = in.nextInt();
      in.nextLine();
      switch (option) {
        case 1:
          beliProduk();
          break;
        case 2:
          historyPembelian();
          break;
        case 3:
          tambahUang();
          break;
        case 4:
          cekUang();
          break;
        case 5:
          currentUser = null;
          exit = true;
          break;
      }
    }
  }

  private boolean isLeap(int year) {
    return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
  }

  private boolean isValidDate(int d, int m, int y) {
    if (y > 9999 || y < 1800) return false;
    if (m < 1 || m > 12) return false;
    if (d < 1 || d > 31) return false;
    if (m == 2) {
      if (isLeap(y)) return (d <= 29);
      else return (d <= 28);
    }
    if (m == 4 || m == 6 || m == 9 || m == 11) return (d <= 30);
    return true;
  }

  private boolean isValidType(String namaProduk) {
    String type = getProdukType(namaProduk);
    if (type.equals("[F]") || type.equals("[C]") || type.equals("[T]")) return true;
    sendMessage("Nama produk harus diakhiri ([F]/[C]/[T])!!!");
    return false;
  }

  private String getProdukType(String namaProduk) {
    return namaProduk.substring(namaProduk.length()-3, namaProduk.length());
  }

  private boolean validateNamaProduk(String namaProduk) {
    int len = namaProduk.length();
    if (len < 3 || len > 16) {
      sendMessage("Nama produk hanya berisi (3-16) character!!!");
      return false;
    }
    return isValidType(namaProduk);
  }

  private boolean validateHargaProduk(int hargaProduk) {
    return (hargaProduk >= 1000);
  }

  private boolean validateVersion(String version) {
    Pattern p = Pattern.compile("^(\\d+\\.)?(\\d+\\.)?(\\d+)$");
    Matcher m = p.matcher(version);
    return m.matches();
  }

  private void tambahProduk() {
    String namaProduk = null, type = null;
    int hargaProduk;

    while (true) {
      clearScr();
      System.out.print("Masukan nama produk : ");
      namaProduk = in.nextLine();
      if (validateNamaProduk(namaProduk)) break;
    }

    while (true) {
      System.out.print("Masukan harga produk : ");
      hargaProduk = in.nextInt();
      in.nextLine();
      if (validateHargaProduk(hargaProduk)) break;
    }

    type = getProdukType(namaProduk);
    if (type.equals("[F]")) {
      Food newFood = new Food();

      newFood.setNamaProduk(namaProduk);
      newFood.setHargaProduk(hargaProduk+(hargaProduk*10/100));

      int date, month, year;
      while (true) {
        System.out.print("Masukan tanggal expired : ");
        date = in.nextInt();
        System.out.print("Masukan bulan expired : ");
        month = in.nextInt();
        System.out.print("Masukan tahun expired : ");
        year = in.nextInt();
        in.nextLine();
        if (isValidDate(date, month, year)) break;
        sendMessage("Tanggal tidak valid!!!");
      }

      newFood.setExpireDate(date, month, year);
      produkList.add(newFood);
    } else if (type.equals("[C]")) {
      Cloth newCloth = new Cloth();

      newCloth.setNamaProduk(namaProduk);
      newCloth.setHargaProduk(hargaProduk+(hargaProduk*25/100));

      String size;
      while (true) {
        System.out.print("Masukan ukuran pakaian : ");
        size = in.nextLine();
        size = size.toUpperCase();
        if (size.equals("S") || size.equals("M") || size.equals("L") || size.equals("XL")) break;
        sendMessage("Ukuran pakaian antara (S/M/L/XL)!!!");
      }

      newCloth.setSize(size);
      produkList.add(newCloth);
    } else if (type.equals("[T]")) {
      Tech newTech = new Tech();

      newTech.setNamaProduk(namaProduk);
      newTech.setHargaProduk(hargaProduk+(hargaProduk*30/100));

      String version;
      while(true) {
        System.out.print("Masukan versi (format: \"version\".\"release\".\"modification\" tanpa tanda kutip) : ");
        version = in.nextLine();
        if (validateVersion(version)) break;
        sendMessage("Versi tidak valid!!!");
      }

      newTech.setVersion(version);
      produkList.add(newTech);
    }
  }

  private void admin() {
    boolean exit = false;
    while (!exit) {
      clearScr();
      System.out.println("Admin Menu");
      System.out.println("==========");
      System.out.println("1. Tambah produk");
      System.out.println("2. Exit");
      System.out.print(">> ");
      int option = in.nextInt();
      in.nextLine();
      switch (option) {
        case 1:
          tambahProduk();
          break;
        case 2:
          exit = true;
          break;
        default:
          sendMessage("Invalid input!!!");
          break;
      }
    }
  }

  private void login() {
    clearScr();
    String username, password = null;

    System.out.println("Login");
    System.out.println("========");

    System.out.print("Username : ");
    username = in.nextLine();
    if (!userList.containsKey(username) && !username.equals("admin")) {
      sendMessage("User tidak teregistrasi dalam aplikasi");
    } else {
      System.out.print("Password : ");
      password = in.nextLine();
      if (username.equals("admin")) {
        if (password.equals("admin123")) admin();
        else sendMessage("Password salah...");
        return;
      }
      try {
        currentUser = userList.get(username);
        currentUser.passwordValidation(password);
        sendMessage("Login berhasil!!!");
      } catch (Exception e) {
        currentUser = null;
        sendMessage(e.getMessage());
      }
    }
  }

  private void buatAkun() {
    clearScr();
    System.out.println("Register");
    System.out.println("========");

    User newUser = new User();

    while (true) {
      System.out.print("Masukan username : ");
      String username = in.nextLine();
      try {
        newUser.setUsername(username);
        break;
      } catch (Exception e) {
        sendMessage(e.getMessage());
      }
    }

    while (true) {
      System.out.print("Masukan nama panjang : ");
      String namaPanjang = in.nextLine();
      try {
        newUser.setNamaPanjang(namaPanjang);
        break;
      } catch (Exception e) {
        sendMessage(e.getMessage());
      }
    }

    while (true) {
      System.out.print("Masukan Alamat Email : ");
      String emailAddress = in.nextLine();
      try {
        newUser.setEmailAddress(emailAddress);
        break;
      } catch (Exception e) {
        sendMessage(e.getMessage());
      }
    }

    while (true) {
      System.out.print("Masukan Password : ");
      String password = in.nextLine();
      try {
        newUser.setPassword(password);
        break;
      } catch (Exception e) {
        sendMessage(e.getMessage());
      }
    }

    userList.put(newUser.getUsername(), newUser);
  }

  private void menu() {
    System.out.println("Padie Shop");
    System.out.println("============");
    System.out.println("1. Login");
    System.out.println("2. Buat akun");
    System.out.println("3. Exit");
    System.out.print(">> ");
  }

  private void sendMessage(String message) {
    System.out.println(message);
    freezePrompt();
  }

  private void freezePrompt() {
    System.out.print("Enter to continue...");
    in.nextLine();
  }

  private void clearScr() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  App() {
    boolean exit = false;
    while (!exit) {
      clearScr();
      menu();
      int option = in.nextInt();
      in.nextLine();
      switch(option) {
        case 1:
          login();
          if (currentUser != null) mainApp();
          break;
        case 2:
          buatAkun();
          break;
        case 3:
          exit = true;
          break;
        default:
          sendMessage("Input tidak valid!!");
          break;
      }
    }
  }

  // debug (data awal buat testing)
  private static void load() {
    Food newFood = new Food("Coca cola [F]",12000,12,12,2022);
    Cloth newCloth = new Cloth("Bape X series [C]",7900000,"L");
    Tech newTech = new Tech("Mechanical key [T]",200000,"1.1.2");
    produkList.add(newFood);
    produkList.add(newCloth);
    produkList.add(newTech);
    userList.put("peter", new User("peter","peter","pet@gmail.com","pet1234",1000000));
  }

  public static void main(String[] args) {
    load();
    new App();
  }
}
