package gtPlusPlus.api.interfaces;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IGregtechPacketEntity {

    void writePacketData(DataOutputStream data) throws IOException;

    void readPacketData(DataInputStream data) throws IOException;
}
