package bank.bankieren;

import bank.internettoegang.ICentraleBank;
import fontys.util.*;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank implements IBank {

    private static final long serialVersionUID = -8728841131739353765L;
    private Map<Integer, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;
    private Lock bankLock = new ReentrantLock();
    private ICentraleBank centrale;

    public Bank(String name, ICentraleBank centrale) {
        accounts = new HashMap<Integer, IRekeningTbvBank>();
        clients = new ArrayList<IKlant>();
        nieuwReknr = 100000000;
        this.name = name;
        this.centrale = centrale;
    }

    @Override
    public int openRekening(String name, String city) {
        if (name.equals("") || city.equals("")) {
            return -1;
        }

        nieuwReknr = centrale.nextBankNr();
        
        IKlant klant = getKlant(name, city);
        IRekeningTbvBank account = new Rekening(nieuwReknr, klant, Money.EURO);
        accounts.put(nieuwReknr, account);
        return nieuwReknr;
    }

    private IKlant getKlant(String name, String city) {
        for (IKlant k : clients) {
            if (k.getNaam().equals(name) && k.getPlaats().equals(city)) {
                return k;
            }
        }
        IKlant klant = new Klant(name, city);
        clients.add(klant);
        return klant;
    }

    @Override
    public IRekening getRekening(int nr) {
        return accounts.get(nr);
    }

    @Override
    public boolean maakOver(int source, int destination, Money money)
            throws NumberDoesntExistException {
        bankLock.lock();
        try {
            if (source == destination) {
                throw new RuntimeException(
                        "cannot transfer money to your own account");
            }
            if (!money.isPositive()) {
                throw new RuntimeException("money must be positive");
            }

            IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
            if (source_account == null) {
                throw new NumberDoesntExistException("account " + source
                        + " unknown at " + name);
            }

            IRekeningTbvBank dest_account;
            dest_account = (IRekeningTbvBank) getRekening(destination);
            if (dest_account == null) {
                dest_account = (IRekeningTbvBank) centrale.getRekening(destination);
                if(dest_account == null)
                {
                    throw new NumberDoesntExistException("account " + destination
                        + " unknown at " + name);
                }
                else
                {
                    boolean gelukt = centrale.maakOver(source, destination, money);
                    if(gelukt)
                    {
                        
                        return true;
                    }
                }
            }
            
            Money negative = Money.difference(new Money(0, money.getCurrency()),
                    money);
            boolean success = source_account.muteer(negative);
            if (!success) {
                return false;
            }
            
            success = dest_account.muteer(money);

            if (!success) // rollback
            {
                source_account.muteer(money);
            }
            return success;
        } finally {
            bankLock.unlock();
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
