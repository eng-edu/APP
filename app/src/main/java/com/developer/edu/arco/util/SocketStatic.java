package com.developer.edu.arco.util;

import io.socket.client.Socket;

/**
 * Created by edu on 13/05/18.
 */

 public  class SocketStatic {

        private static Socket socket;

        public static synchronized Socket getSocket(){
            return socket;
        }

        public static synchronized void setSocket(Socket socket){
            SocketStatic.socket = socket;
        }

}
