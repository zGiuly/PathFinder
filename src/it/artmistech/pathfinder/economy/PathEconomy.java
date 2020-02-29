package it.artmistech.pathfinder.economy;

import it.artmistech.pathfinder.sqlite.Database;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PathEconomy implements Economy {
    private final Database database;

    public PathEconomy(Database database) {
        this.database = database;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "PathFinder Economy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        try(PreparedStatement statement = database.getConnection().prepareStatement("SELECT * FROM playerEconomy WHERE name = ?")) {
            statement.setString(1, s);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getString("name") != null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        try(PreparedStatement statement = database.getConnection().prepareStatement("SELECT * FROM playerEconomy WHERE name = ?")) {
            statement.setString(1, s);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getDouble("balance");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String s, String s1) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return 0;
    }

    @Override
    public boolean has(String s, double v) {
        if(getBalance(s) >= v) return true;
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        try(PreparedStatement statement = database.getConnection().prepareStatement("UPDATE playerEconomy SET balance = ? WHERE name = ?")) {
            double balance = getBalance(s)-v;
            statement.setDouble(1, balance);
            statement.setString(2, s);

            statement.executeUpdate();

            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE, "Database error");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        try(PreparedStatement statement = database.getConnection().prepareStatement("UPDATE playerEconomy SET balance = ? WHERE name = ?")) {
            double balance = getBalance(s)+v;
            statement.setDouble(1, balance);
            statement.setString(2, s);

            statement.executeUpdate();

            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE, "Database error");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        try(PreparedStatement statement = database.getConnection().prepareStatement("INSERT INTO playerEconomy (?,?)")) {
            statement.setString(1, s);
            statement.setDouble(2, 0.0);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return createPlayerAccount(offlinePlayer.getName());
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
