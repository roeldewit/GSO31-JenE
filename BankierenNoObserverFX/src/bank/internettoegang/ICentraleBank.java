/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

/**
 *
 * @author Joris (javadoc)
 */
public interface ICentraleBank{

    /**
     * Maakt een bedrag over van rekening1 bij bank1 naar rekening2 bij bank2,
     * mits het afschrijven van het bedrag van de rekening met nr bron niet
     * lager wordt dan de kredietlimiet van deze rekening
     *
     * @param rek1 bron
     * @param bank1 bron bank
     * @param rek2 doel
     * @param bank2 doel bank
     * @param bedrag het over te maken bedrag
     * @return true als gelukt, false als gefaald
     */
    public boolean maakOver(int rek1, int rek2, Money bedrag);
    
    public int nextBankNr();
    
    public boolean addBank(IBank bank);
    
    public IRekening getRekening(int rekenNr);
    
}
