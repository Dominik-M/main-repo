/*
 * Copyright (C) 2016 Dundun
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package utils;

/**
 *
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.06.2016
 */
public class Interpreter {
    
    enum Command{
        
    }
    
    public static boolean execute(Command cmd, String... params){
        switch(cmd){
            default:
                return false;
        }
    }
    
    public static boolean execute(String in){
        String[] args = in.split(" ");
        if(args.length > 0){
            Command cmd = Command.valueOf(args[0].trim().toUpperCase());
            String[] params = new String[args.length-1];
            for(int i=0; i<params.length; i++){
                params[i] = args[i+1].trim();
            }
            return execute(cmd,params);
        }else return false;
    }
}
