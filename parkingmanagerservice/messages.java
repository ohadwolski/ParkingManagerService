
@@ -0,0 +1,58 @@
package parkingmanagerservice;

import java.util.Date;

enum MessageType {
    ECHO, ACKECHO, CARGETPARK, SETPARK, SETSIGN, CARFREEPARK, FREEPARK, SETSYMBOL,OPENELEMENT,CLOSEELEMENT , PARKTYPE, ADDELEMENT, REMOVEELEMENT  ;
}
enum LedColour{
	NONE, RED, GREEN, BLUE;
}


public class messages {
    private int Id;
    private Date Date;
    private MessageType Type;
    private int Num;
    private LedColour Light;
    private int Symbol;
    private int ParkType;

    public messages(int id, Date date, MessageType type, int num, LedColour light, int symbol,int parkType) {
        Id = id;
        this.Date = date;
        Type = type;
        Num=num;
        Light=light;
        Symbol=symbol;
        ParkType=parkType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean compareId(int id) {
        return Id == id;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public boolean compareDate(Date date) {
        return this.Date == date;
    }

    public MessageType getType() {
        return Type;
    }

    public void setType(MessageType type) {
        Type = type;
    }

    public boolean compareType(MessageType type) {
        return this.Type == type;
    }

    public boolean compare(messages msg) {
        return this.compareDate(msg.getDate()) && this.compareId(msg.getId()) && this.compareType(msg.getType());
    }
    
    public void setNum(int num) {
        Num = num;
    }
    public void getNum(int num) {
    	Num = num;
    }
    public boolean compareNum(int num) {
        return Num == num;
    }
    public void setLight(LedColour light) {
        Light = light;
    }
    public void getLight(LedColour light) {
    	 Light = light;
    }
    public boolean comparesLight(LedColour light) {
        return  Light == light;;
    }
    public void setSymbol(int symbol) {
        Symbol = symbol;
    }
    public void getSymbol(int symbol) {
    	Symbol = symbol;
    }
    public boolean compareSymbol(int symbol) {
        return Symbol == symbol;
    }
    public void setParkType(int parkType) {
    	ParkType = parkType;
    }
    public void getParkType(int parkType) {
    	ParkType = parkType;
    }
    public boolean compareParkType(int parkType) {
        return ParkType == parkType;
    }
}