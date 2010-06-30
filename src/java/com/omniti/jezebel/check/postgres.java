package com.omniti.jezebel.check;
import com.omniti.jezebel.check.JDBC;
import com.omniti.jezebel.JezebelCheck;
public class postgres extends JDBC implements JezebelCheck {
  static { try { Class.forName("org.postgresql.Driver"); }
           catch (Exception e) { throw new RuntimeException(e); } }
  protected String defaultPort() { return "5432"; }
  protected String jdbcConnectUrl(String host, String port, String db) {
    return "jdbc:postgresql://" + host + ":" + port + "/" + db;
  }
}
