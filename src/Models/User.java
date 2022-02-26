package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Main.*;

public class User {
  private String username;
  private String namaPanjang;
  private String emailAddress;
  private String password;
  private ArrayList<Produk> shopingCart = new ArrayList<Produk>();
  private HashMap<Integer,Struk> strukList = new HashMap<Integer,Struk>();
  private int balance;

  public User(String username, String namaPanajang, String emailAddress, String password, int balance) {
    this.username = username;
    this.namaPanjang = namaPanajang;
    this.emailAddress = emailAddress;
    this.password = password;
    this.balance = balance;
  }

  public User() {
    this(null,null,null,null,1000);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) throws Exception {
    int len = username.length();
    if (len < 3 || len > 16) {
      throw new Exception("Username harus berisi (3-16) character!!!");
    } else if (App.userList.containsKey(username)) {
      throw new Exception("Username telah digunakan!!!");
    }
    this.username = username;
  }

  public String getNamaPanjang() {
    return namaPanjang;
  }

  public void setNamaPanjang(String namaPanjang) throws Exception {
    Pattern p = Pattern.compile("^[a-zA-Z0-9]{3,16}$");
    Matcher m = p.matcher(namaPanjang);
    if (!m.matches()) throw new Exception("Nama panjang hanya berisi (3-16) character dan tidak mengandung character special!!!");
    this.namaPanjang = namaPanjang;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) throws Exception {
    int len = emailAddress.length();
    if (len < 5 ||len > 30) throw new Exception("Email hanya berisi (5-30) character!!!");
    Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.(com|net)");
    Matcher m = p.matcher(emailAddress);
    if (!m.matches()) throw new Exception("Email hanya menerima top level domain .com / .net");
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) throws Exception {
    Pattern p = Pattern.compile("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,40})$");
    Matcher m = p.matcher(password);
    if (!m.matches()) throw new Exception("Password hanya berisi (8-40) characters dan merupakan kombinasi huruf dan angka!!!");
    this.password = password;
  }

  public void passwordValidation(String password) throws Exception {
    if (!this.password.equals(password)) throw new Exception("Password salah...");
  }

  public void showCart() {
    int number = 1;
    for (Produk produk : this.shopingCart) {
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
  }

  public boolean emptyCart() {
    return this.shopingCart.size() == 0;
  }

  public void addToCart(Produk newProduk) {
    this.shopingCart.add(newProduk);
  }

  public ArrayList<Produk> getShopingCart() {
    return this.shopingCart;
  }

  public void clearShopingCart() {
    this.shopingCart.clear();
  }

  public int getTotalCart() {
    int total = 0;
    for (Produk p : this.shopingCart) {
      total += p.hargaProduk;
    }
    return total;
  }

  public int getBalance() {
    return this.balance;
  }

  public void checkOut(int total) {
    Struk newStruk = new Struk(this.shopingCart, total);
    newStruk.cetakStruk();
    this.balance -= total;
    this.strukList.put(newStruk.getStrukId(), newStruk);
    clearShopingCart();
  }

  public void printStrukId() {
    int number = 1;
    for (Integer key : strukList.keySet()) {
      System.out.printf("%d. %d\n",number++,key);
    }
  }

  public boolean strukIdExist(int id) {
    return this.strukList.containsKey(id);
  }

  public Struk getStruk(int id) {
    return this.strukList.get(id);
  }

  public void addBalance(int nominal) {
    this.balance += nominal;
  }
}
