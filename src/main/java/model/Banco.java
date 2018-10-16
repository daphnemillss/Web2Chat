/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caiot
 */
public class Banco {

    public static boolean login(String user, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM public.\"user\" WHERE usuario=? AND senha=?";

        Connection con = Conexao.getConnection();
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, user);
        psmt.setString(2, senha);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            psmt.close();
            rs.close();
            con.close();

            return true;
        } else {
            psmt.close();
            rs.close();
            con.close();

            return false;
        }

    }

    public static boolean cadastrar(String user, String email, String senha) throws SQLException {
        String sql = "INSERT INTO public.\"user\" VALUES (?, ?, ?)";

        Connection con = Conexao.getConnection();
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, user);
        psmt.setString(2, email);
        psmt.setString(3, senha);
        //System.out.println(cadastrar.getUsuario() + "/" + cadastrar.getEmail() + "/" + cadastrar.getSenha());

        int i = psmt.executeUpdate();
        if (i != 0) {
            psmt.close();
            con.close();

            return true;
        } else {
            psmt.close();
            con.close();

            return false;
        }

    }

    public static boolean escreveMensagem(String from, String msg, String to, Timestamp date) throws SQLException {
        String sql = "INSERT INTO public.\"mensagem\" values (?, ?, ?, ?)";

        Connection con = Conexao.getConnection();
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, from);
        psmt.setString(2, msg);
        psmt.setString(3, to);
        psmt.setTimestamp(4, date);

        int i = psmt.executeUpdate();
        if (i != 0) {
            psmt.close();
            con.close();

            return true;
        } else {
            psmt.close();
            con.close();

            return false;
        }

    }

    public static ArrayList<String> getContatos(String user) throws SQLException {
        if (user == null) {
            return null;
        }

        ArrayList<String> contatos = new ArrayList<>();
        String sql = "SELECT usuario FROM public.\"user\" where usuario != '" + user + "'";
        Statement st;
        ResultSet rs;
        try (Connection con = Conexao.getConnection()) {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                contatos.add(rs.getString("usuario"));
            }
        }
        st.close();
        rs.close();

        return contatos;
    }

    public static List<MsgObject> buscaMensagens(String user1, String user2) throws SQLException, IOException {
        if (user1 == null || user2 == null) {
            return null;
        }

        String sql = "select * from public.\"mensagem\" where (remetente = ? or remetente = ?) and (destinatario = ? or destinatario = ?) order by data asc";
        Connection con = Conexao.getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, user1);
        st.setString(2, user2);
        st.setString(3, user1);
        st.setString(4, user2);

        ResultSet rs = st.executeQuery();
        List<MsgObject> lista = new ArrayList<>();

        if (rs.next()) {
            do {
                MsgObject msg = new MsgObject();

                msg.setDate(rs.getTimestamp("data"));
                msg.setFrom(rs.getString("remetente"));
                msg.setMessage(rs.getString("texto"));
                msg.setTo(rs.getString("destinatario"));

                lista.add(msg);
            } while (rs.next());

            return lista;
        } else {
            st.close();
            con.close();

            return null;
        }
    }

    public static boolean deleteAccount(String user) throws SQLException {
        if (user == null) {
            return false;
        }

        Connection con = Conexao.getConnection();
        String deleteMsgs = "delete from public.\"mensagem\" where remetente = ? or destinatario = ?";
        PreparedStatement psmt = con.prepareStatement(deleteMsgs);
        psmt.setString(1, user);
        psmt.setString(2, user);

        int i = psmt.executeUpdate();

        String deleteUser = "delete from public.\"user\" where usuario = ?";
        psmt = con.prepareStatement(deleteUser);
        psmt.setString(1, user);

        i = psmt.executeUpdate();

        if (i == 0) {
            con.close();
            psmt.close();

            return false;
        } else {
            con.close();
            psmt.close();

            return true;
        }

    }

}
