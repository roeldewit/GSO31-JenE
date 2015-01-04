/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.*;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joris
 */
public class CentraleBank implements ICentraleBank {
    
    private int nextRekNr = 100000000;
    private List<IBank> banken;

    public CentraleBank() {
        banken = new ArrayList<>();
    }

    @Override
    public boolean maakOver(int rekAfz, int rekBest, Money bedrag)
    {
        boolean afzenderGelukt = false;
        boolean bestemmingGelukt = false;
        IRekeningTbvBank rekeningAfzender = (IRekeningTbvBank) getRekening(rekAfz);
        IRekeningTbvBank rekeningBestemming = (IRekeningTbvBank) getRekening(rekBest);
        if(rekeningAfzender != null && rekeningBestemming != null)
        {
            Money negative = Money.difference(new Money(0, bedrag.getCurrency()), bedrag);
            afzenderGelukt = rekeningAfzender.muteer(negative);
            bestemmingGelukt = rekeningBestemming.muteer(bedrag);
            
            if(afzenderGelukt && bestemmingGelukt)
            {
                return true;
            }
            else if(afzenderGelukt && bestemmingGelukt == false)
            {
                rekeningAfzender.muteer(bedrag);
                return false;
            }
            else if(afzenderGelukt == false && bestemmingGelukt)
            {
                rekeningBestemming.muteer(negative);
                return false;
            }
        }
        return false;
    }

    @Override
    public int nextBankNr()
    {
        nextRekNr++;
        return nextRekNr;
    }

    @Override
    public boolean addBank(IBank bank)
    {
        if(bank != null)
        {
            banken.add(bank);
            return true;
        }
        return false;
    }

    @Override
    public IRekening getRekening(int rekenNr)
    {
        for(IBank bank : banken)
        {
            IRekening rekening = bank.getRekening(rekenNr);
            if(rekening != null)
            {
                return rekening;
            }
        }
        return null;
    }

}
