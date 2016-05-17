/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


public abstract class PriceField extends Input {

// --------------------------- CONSTRUCTORS ---------------------------

    public PriceField() {
        super(null, "");
    }

// -------------------------- OTHER METHODS --------------------------

//    {
//        try {
//            Price price = (Price)getValue();
//            if (price == null) return;
//            Price newprice = price.copyPrice();
//            newprice.setPrice(getText());
//            double value = newprice.getPrice();       
//            boolean ok =false;
//            newprice.setPrice(Math.floor(value));
//            while (newprice.getPrice() <= value)
//            {
//                if (newprice.getPrice() == value)
//                {
//                    ok = true;
//                    break;
//                }
//                newprice = newprice.tickDown(price.getNumberTicks()); 
//            }
//            if (ok)
//            {
//                int pos =getPosController().getOffset();
//                setValue(newprice);
//                getPosController().seek(pos);
//
//            }
//            else
//            {
//                setText(prevText);
//                getPosController().seek(prevPos);  
//            }
//        }
//        catch (NumberFormatException nfe)
//        {
//            
//            setText(prevText);
//            getPosController().seek(prevPos);
//            
//        }
//
//    }


    public abstract Object fromString(String string);


    protected boolean isValidCh(char ch) {
        String text = getText();
        int pos = getPosController().getOffset();
        boolean hasPoint = (text.indexOf('.') > -1);
        boolean hasPlus = (text.indexOf('+') > -1);
        if (Character.isDigit(ch) && ch != '0' && !hasPlus) {
            return true;
        }
        if (ch == '.' && !hasPoint && !hasPlus) {
            return true;
        }
        if (ch == '0' && text.length() > 0 && !hasPlus) {
            return true;
        }
        if (pos == text.length() && ch == '+' && !hasPlus) {
            return true;
        }
        return false;
    }


    protected abstract void rework(int prevPos, String prevText);
//    {
//        Price price = ((Price)getValue()).copyPrice();
//        price.setPrice(string);
//        return price;
//    }
    public abstract String toString(Object price);
//    {
//        return ((Price)price).toPriceString();
//    }


}