package tubessdt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JOptionPane;

class Customer {

    String nama;
    String password;
    String noHp;
    Kendaraan[] motor = new Kendaraan[3];
    int jumlahKendaraan = 0;
}

class ReportCustomer {

    String nama;
    String report;

    public ReportCustomer(String nama, String report) {
        this.nama = nama;
        this.report = report;
    }
}

class Kendaraan {

    String jenisMotor;
    String platNomor;
}

class Admin {

    String nama;
    String password;
}

class Service {

    String[] sparepart;
    boolean statusService = false;
    double hargaTotal;
    int indexKendaraan;
    Customer customer;
}

class Sparepart {

    String nama;
    double harga;

    public Sparepart(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }
}

class Antrian {

    String nama;
    int noAntri;

    public Antrian(String nama, int noAntri) {
        this.nama = nama;
        this.noAntri = noAntri;
    }
}

class Node {

    Admin dataAdmin = new Admin();
    Customer dataCustomer = new Customer();
    Service dataService = new Service();
    Node next;
}

public class TubesSDT {

    static Scanner scan = new Scanner(System.in);
    static ArrayList<Antrian> Antrian = new ArrayList<Antrian>();
    static LinkedList<ReportCustomer> report = new LinkedList<ReportCustomer>();
    static LinkedList<Admin> dataAdmin = new LinkedList<Admin>();
    static int noAntrian = 0;
    static Node headAdmin = null;
    static Node headCustomer = null;
    static Node headOrder = null;
    static Sparepart[] arrSparepart = {
        new Sparepart("Aki", 140000),
        new Sparepart("Oli", 450000),
        new Sparepart("Ban", 150000),
        new Sparepart("Kampas rem", 100000),
        new Sparepart("Knalpot", 50000),};

    public static void linkedlistAdmin(String nama, String password) {
        Node newNode = new Node();
        newNode.dataAdmin.nama = nama;
        newNode.dataAdmin.password = password;

        if (headAdmin == null) {
            headAdmin = newNode;
        } else {
            Node temp = headAdmin;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public static void defineDataAdmin() {
        String nama;
        String password;
        nama = "michael";
        password = "123";
        linkedlistAdmin(nama, password);
        nama = "tony";
        password = "234";
        linkedlistAdmin(nama, password);
        nama = "kevin";
        password = "345";
        linkedlistAdmin(nama, password);
    }
    
    public static void loginAdmin() {
        String nama;
        String password;
        nama = JOptionPane.showInputDialog(null, "Input nama");
        password = JOptionPane.showInputDialog(null, "Input password");

        if (verifikasiAdmin(nama, password)) {
            JOptionPane.showMessageDialog(null, "Login berhasil");
            menuAdmin();
        } else {
            JOptionPane.showMessageDialog(null, "Login gagal");
            loginAdmin();
        }
    }

    public static boolean verifikasiAdmin(String nama, String password) {
        Node temp = headAdmin;
        while (temp != null) {
            if (temp.dataAdmin.nama.equals(nama) && (temp.dataAdmin.password.equals(password))) {
                return true;
            } else {
                temp = temp.next;
            }
        }
        return false;
    }

    public static void menuAdmin() {
        int menu = 0;
        String nama, password;
        while (menu != 7) {
            menu = Integer.parseInt(JOptionPane.showInputDialog(null, "Menu admin\n 1.Lihat data pembeli\n 2.Lihat Data Pesanan \n 3.Data Service \n 4. Cek Tarif\n 5.Konfirmasi Cek Status \n 6.Lihat Feedback Customer \n 7.Back"));
            switch (menu) {
                case 1:
                    lihatDataPembeli();
                    break;
                case 2:
                    nama = JOptionPane.showInputDialog(null, "Nama:");
                    password = JOptionPane.showInputDialog(null, "Password:");
                    lihatDataPesanan(nama, password);
                    break;
                case 3:
                    dataService();
                    break;
                case 4:
                    nama = JOptionPane.showInputDialog(null, "Nama:");
                    password = JOptionPane.showInputDialog(null, "Password:");
                    cekTarif(nama, password);
                    break;
                case 5:
                    konfirmasiStatusService();
                    break;
                case 6:
                    lihatReport();
                    break;
                case 7:
                    return;
            }
        }
    }

    public static void cekTarif(String nama, String password) {
        Node temp = headOrder;
        double total = 0;
        while (temp != null) {
            if (temp.dataService.customer.nama.equals(nama) && temp.dataService.customer.password.equals(password) && !temp.dataService.statusService) {
                total += temp.dataService.hargaTotal;
            }
            temp = temp.next;
        }
        JOptionPane.showMessageDialog(null, "Nama: " + nama + "\n"
                + "Total bayar: " + total);
    }

    public static void lihatDataPembeli() {
        Node temp = headCustomer;
        while (temp != null) {
            System.out.println(temp.dataCustomer.nama);
            for (int i = 0; i < temp.dataCustomer.jumlahKendaraan; i++) {
                System.out.println(temp.dataCustomer.motor[i].platNomor);
                System.out.println(temp.dataCustomer.motor[i].jenisMotor);
            }
            System.out.println(temp.dataCustomer.noHp);
            temp = temp.next;
            System.out.println("-----");
        }
    }

    public static void lihatDataPesanan(String nama, String password) {
        Node temp = headOrder;
        boolean found = false;
        System.out.println("No.\tNama");
        System.out.println("----------");
        while (temp != null) {
            if (temp.dataService.customer.nama.equals(nama) && temp.dataService.customer.password.equals(password) && !temp.dataService.statusService) {
                found = true;
                for (int i = 0; i < temp.dataService.sparepart.length; i++) {
                    System.out.println((i + 1) + "\t" + temp.dataService.sparepart[i]);
                }
                System.out.println("-------");
            }
            temp = temp.next;
            System.out.println("");
        }
    }

    public static void dataService() {
        Node temp = headOrder;
        while (temp != null) {
            System.out.println("nama: " + temp.dataService.customer.nama);
            System.out.println("Jenis motor: " + temp.dataService.customer.motor[temp.dataService.indexKendaraan].jenisMotor + " " + temp.dataService.customer.motor[temp.dataService.indexKendaraan].platNomor);
            System.out.println("No.\tNama");
            System.out.println("---------------");
            for (int i = 0; i < temp.dataService.sparepart.length; i++) {
                System.out.println((i + 1) + "\t" + temp.dataService.sparepart[i]);
            }
            System.out.println("Total harga: " + temp.dataService.hargaTotal);
            System.out.print("Status: ");
            temp = temp.next;
            System.out.println("");
        }
    }

    public static void print() {
        int panjang = Antrian.size();
        System.out.println("===== Data Antrian =====");
        for (int i = 0; i < panjang; i++) {
            Antrian p = Antrian.get(i);
            System.out.println("Nama Customer: " + p.nama);
            System.out.println("Nomer Antri: " + p.noAntri);
        }
        System.out.println("");
    }

    public static void konfirmasiStatusService() {
        print();
        int panjang = Antrian.size();
        boolean status = false;
        System.out.println("Pilih nomer antrian yang sudah selesai");
        int pilih = scan.nextInt();
        for (int i = 0; i < panjang; i++) {
            Antrian p = Antrian.get(i);
            if (pilih == p.noAntri) {
                status = true;
                System.out.println("Antrian Nomer: " + p.noAntri);
                Antrian.remove(p);
                break;
            }
        }
        System.out.println("");
    }

    public static void lihatReport() {
        Iterator ite = report.iterator();
        System.out.println("====== Hasil Report =========");
        while (ite.hasNext()) {
            ReportCustomer P = (ReportCustomer) ite.next();
            System.out.println("Nama Customer :" + P.nama + "\nFeedBack: " + P.report);
            System.out.println("=========================================");
        }
        System.out.println("");
    }

    public static void linkedlistCustomer(String nama, String password, String noHp, String[] jenisMotor, String[] platNomor, int jumlahKendaraan) {
        Node newNode = new Node();
        newNode.dataCustomer.nama = nama;
        newNode.dataCustomer.password = password;
        newNode.dataCustomer.noHp = noHp;
        newNode.dataCustomer.jumlahKendaraan = jumlahKendaraan;
        for (int i = 0; i < jenisMotor.length; i++) {
            newNode.dataCustomer.motor[i] = new Kendaraan();
            newNode.dataCustomer.motor[i].jenisMotor = jenisMotor[i];
            newNode.dataCustomer.motor[i].platNomor = platNomor[i];
        }

        if (headCustomer == null) {
            headCustomer = newNode;
        } else {
            Node temp = headCustomer;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public static void defineDataCustomer() {
        String nama;
        String password;
        String noHp;
        String[] jenisMotor = new String[3];
        String[] platNomor = new String[3];
        int jumlahKendaraan;

        for (int i = 0; i < jenisMotor.length; i++) {
            jenisMotor[i] = "";
            platNomor[i] = "";
        }
        nama = "Asep";
        password = "123";
        noHp = "089761211";
        jenisMotor[0] = "Beat";
        platNomor[0] = "B 123 AC";
        jumlahKendaraan = 1;
        linkedlistCustomer(nama, password, noHp, jenisMotor, platNomor, jumlahKendaraan);
        for (int i = 0; i < jenisMotor.length; i++) {
            jenisMotor[i] = "";
            platNomor[i] = "";
        }
        nama = "Budi";
        password = "234";
        noHp = "089761211";
        jenisMotor[0] = "Supra";
        platNomor[0] = "C 222 AV";
        jenisMotor[1] = "Vario";
        platNomor[1] = "A 333 AD";
        jumlahKendaraan = 2;
        linkedlistCustomer(nama, password, noHp, jenisMotor, platNomor, jumlahKendaraan);
    }

    public static void menuCustomer() {
        int menu = 0;
        while (menu != 3) {
            menu = Integer.parseInt(JOptionPane.showInputDialog(null, "Menu\n 1.Login\n 2.Registrasi \n 3.Back"));
            switch (menu) {
                case 1:
                    loginCustomer();
                    break;
                case 2:
                    registrasiCustomer();
                    break;
                case 3:
                    return;
            }
        }
    }

    public static void registrasiCustomer() {
        String nama;
        String password;
        String noHp;
        String[] jenisMotor = new String[3];
        String[] platNomor = new String[3];
        int jumlahKendaraan = 0;

        nama = JOptionPane.showInputDialog(null, "Input nama:");
        password = JOptionPane.showInputDialog(null, "Input password");
        noHp = JOptionPane.showInputDialog(null, "Input Nomer Hp");

        boolean status = true;
        int counter = 0;
        for (int i = 0; i < jenisMotor.length; i++) {
            jenisMotor[i] = "";
            platNomor[i] = "";
        }
        while (status) {
            jenisMotor[counter] = JOptionPane.showInputDialog(null, "Input jenis motor:");
            platNomor[counter] = JOptionPane.showInputDialog(null, "Input plat nomor:");
            jumlahKendaraan++;
            if (counter <= 2) {
                String cek = JOptionPane.showInputDialog(null, "Mau lanjut apa tidak? (ya/tidak)");
                if (cek.equals("tidak")) {
                    status = false;
                }
            }
            counter++;

        }
        linkedlistCustomer(nama, password, noHp, jenisMotor, platNomor, jumlahKendaraan);
    }

    public static void loginCustomer() {
        String nama;
        String password;
        nama = JOptionPane.showInputDialog(null, "Input nama");
        password = JOptionPane.showInputDialog(null, "Input password");

        if (verifikasiCustomer(nama, password)) {
            menuPesanan(nama, password);
            JOptionPane.showInputDialog(null, "Login berhasil");
        } else {
            loginCustomer();
            JOptionPane.showInputDialog(null, "Login gagal");
        }
    }

    public static boolean verifikasiCustomer(String nama, String password) {
        Node temp = headCustomer;
        while (temp != null) {
            if (temp.dataCustomer.nama.equals(nama) && (temp.dataCustomer.password.equals(password))) {
                return true;
            } else {
                temp = temp.next;
            }
        }
        return false;
    }

    public static void menuPesanan(String nama, String password) {
        int menu = 0;
        while (menu != 5) {
            menu = Integer.parseInt(JOptionPane.showInputDialog(null, "pilih menu \n 1.Harga sparepart \n 2.service  \n 3.Cek status motor \n 4.Feedback \n 5.Back"));
            switch (menu) {
                case 1:
                    listHarga();
                    break;
                case 2:
                    service(nama, password);
                    break;
                case 3:
                    lihatStatusService(nama, noAntrian);
                    break;
                case 4:
                    inputReportCustomer();
                    break;
                case 5:
                    return;
            }
        }
    }

    public static void service(String nama, String password) {
        Node temp = headCustomer;
        while (temp != null) {
            if (temp.dataCustomer.nama.equals(nama) && temp.dataCustomer.password.equals(password)) {
                break;
            }
            temp = temp.next;
        }
        if (temp.dataCustomer.jumlahKendaraan == 1) {
            pilihSparepart(temp, 0, nama, password);
        } else {
            for (int i = 0; i < temp.dataCustomer.jumlahKendaraan; i++) {
                System.out.println((i + 1) + ". " + temp.dataCustomer.motor[i].jenisMotor + " " + temp.dataCustomer.motor[i].platNomor);
            }
            System.out.println("Pilih motor yang akan di service");
            int inputMotor = scan.nextInt() - 1;
            pilihSparepart(temp, inputMotor, nama, password);
            System.out.println("");
        }
    }

    public static void pilihSparepart(Node temp, int noKendaraan, String nama, String password) {
        int inputSparepart = 0;
        double hargaSparepartTot = 0;
        char lanjutOrder = ' ';
        String[] orderedItem = new String[arrSparepart.length];
        int counter = 0;
        while (lanjutOrder != 'n' || counter == 5) {
            System.out.println("Pilih sparepart : ");
            inputSparepart = scan.nextInt() - 1;
            if (inputSparepart > arrSparepart.length - 1 || inputSparepart < 0) {
                System.out.println("salah input!");
            } else {
                orderedItem[counter] = arrSparepart[inputSparepart].nama;
                counter++;
                hargaSparepartTot += arrSparepart[inputSparepart].harga;
                System.out.println("harga sparepart: " + hargaSparepartTot);
                System.out.println("mau lanjut?(y/n): ");
                lanjutOrder = scan.next().charAt(0);
            }
        }

        insertOrder(temp.dataCustomer, hargaSparepartTot, noKendaraan, orderedItem);
        System.out.println("order anda sedang di proses!");
        System.out.println("");

        noAntrian++;
        Antrian data = new Antrian(nama, noAntrian);
        tambahAntrian(data);
    }

    public static void insertOrder(Customer customer, double hargaTotal, int indexKendaraan, String[] orderedItem) {
        Node newNode = new Node();
        newNode.dataService.customer = customer;
        newNode.dataService.hargaTotal = hargaTotal;
        newNode.dataService.indexKendaraan = indexKendaraan;
        newNode.dataService.sparepart = orderedItem;

        if (headOrder == null) {
            headOrder = newNode;
        } else {
            Node temp = headOrder;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }

    }

    public static void listHarga() {
        System.out.println("\t LIST HARGA");
        System.out.println("======================================");
        System.out.println("No. \t Nama \t Harga");
        for (int i = 0; i < arrSparepart.length; i++) {
            System.out.println((i + 1) + ".\t" + arrSparepart[i].nama + "\t" + arrSparepart[i].harga);
        }
    }

    public static void tambahAntrian(Antrian data) {
        Antrian.add(data);
    }

    public static void lihatStatusService(String nama, int noAntri) {
        int count = 0, panjang = Antrian.size();
        boolean status = false;
        System.out.println("======= Data Antrian =======");
        for (int i = 0; i < panjang; i++) {
            Antrian p = Antrian.get(i);
            count++;
            if (p.nama.equals(nama)) {
                status = true;
                System.out.println("Nama Customer :" + p.nama);
                System.out.println("Nomer Antrian :" + p.noAntri);
                System.out.println("Antrian Masih Sisa :" + (count - 1));
                System.out.println("");
                break;
            }
        }
        if (status = false) {
            System.out.println("Anda tidak ada didalam antrian");
            System.out.println("");
        }
    }

    public static void inputReportCustomer() {
        System.out.println("======= Report =======");
        System.out.println("Masukan Nama Anda: ");
        String nama = scan.next();
        System.out.println("Tuliskan Report Anda: ");
        String report = scan.next();

        ReportCustomer data = new ReportCustomer(nama, report);
        insertReport(data);
    }

    public static void insertReport(ReportCustomer data) {
        report.add(data);
    }

    public static void main(String[] args) {
        // Job description
        // - 1121009 - Kevin Nathaniel: membuat konfirmasi status service menggunakan collection array list
        // - 1121035 - Anthony Kevin: membuat menu feedback menggunakan collection link listed
        // - 1121047 - Michael Setiawan: membuat menu antrian menggunakan collection array listed
        defineDataAdmin();
        defineDataCustomer();

        int menu = 0;
        while (menu != 3) {
            menu = Integer.parseInt(JOptionPane.showInputDialog(null, "Menu\n 1.Admin\n 2.Customer \n 3.Exit"));
            switch (menu) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    menuCustomer();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Keluar Program");
            }
        }
    }
}
