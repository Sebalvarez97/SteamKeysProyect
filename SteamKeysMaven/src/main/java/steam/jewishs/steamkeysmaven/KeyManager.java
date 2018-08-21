/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steam.jewishs.steamkeysmaven;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import Interface.AddKey;
import Interface.Configurations;
import Interface.Inventory;
import Model.Key;
import Model.KeyState;
import Model.KeyType;
import Model.SteamParameters;
import Model.Trade;
import TransporterUnits.*;
import java.awt.Window;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



public class KeyManager { 
    
 //INGRESA UNA NUEVA KEY
    public static void EnterKey(KeyDTO ktu){
        try{
         KeyState ks = EntityController.find(new KeyState(ktu.getState()));
         KeyType kt = EntityController.find(new KeyType(ktu.getType()));
        if(ks != null && kt != null){ 
            Key k = new Key();
            k.setBuyDate(ConvertDate(ktu.getbuydate())); //PERMITE GUARDAR EN BASE DE DATOS LA FECHA PARA STEAM
            k.setKeyState(ks);
            k.setKeyType(kt);
            EntityController.create(k);
        }else {
            throw new NonexistentEntityException("The type or the state does not exist");
        }
        }catch(NonexistentEntityException Ne){
            //se enviaran al ExceptionManager
        }
    }
    //DEVUELVE LA LLAVE SEGUN EL ID INGRESADO EN EL KEYDTO PATRON
    public static KeyDTO getKey(KeyDTO patron) throws NonexistentEntityException{
        KeyDTO ret = null;
        Iterator iter = ListKeys().iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(dto.getId() == patron.getId()){
                ret = dto;
            }
        }
        return ret;
    }
//BORRA LA KEY
    public static void DeleteKey(KeyDTO ktu){
        Key key = new Key();
        key.setId(ktu.getId());
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
           //se enviaran al ExceptionManager
        }  
    }
    //DEVUELVE SI ES TRADEABLE LA LLAVE
    private static boolean isTradeable(Key key){
        Date datenow = Calendar.getInstance().getTime();
        datenow = ConvertDate(datenow);
        Date release = ReleaseDate(key.getBuyDate());
        if(datenow.after(release)){
            return true;
        }else{
            return false;
        }
        
    }//FECHA DE LIBERACION
    public static Date ReleaseDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 8);
        
        return cal.getTime();
    }
    //ACTUALIZA EL ESTADO DE LAS LLAVES DEL INVENTARIO 
    public static void UpdateState(){
        try {
            List<Key> keys = EntityController.ListKeys();
            Key key;
            Iterator iter = keys.iterator();
            while(iter.hasNext()){
                key = (Key) iter.next();
                if(isTradeable(key)){
                    key.setKeyState(EntityController.find(new KeyState("Tradeable")));
                    EntityController.Edit(key);
                }               
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(KeyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KeyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //DEVUELVE LA DIFERENCIA EN DIAS
    public static int DayDiference(Date date1, Date date2){
        long startime = date1.getTime();
        long endtime = date2.getTime();
        long diff = endtime - startime;
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        
    }
    //GENERAR UN STRING CON LA FECHA
    public static String SimpleFormatDate(Date date){
        String returned;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1;
        int year = cal.get(Calendar.YEAR);
        returned = day + "-" +month  + "-" + year;
        return returned;
    }
    //DEVUELVE LA CANTIDAD DE LLAVES CON CIERTO ESTADO
    public static int CantWithState(TypeStateDTO dto) throws NonexistentEntityException{
        List<KeyDTO> keys = ListKeys();
        Iterator iter = keys.iterator();
        KeyDTO k;
        int cantidad = 0;
        while(iter.hasNext()){
            k = (KeyDTO) iter.next();
            if(k.getState().equals(dto.getDescription())){
                cantidad++;
            }
        }
        return cantidad;
    }
//DEVUELVE LA FECHA CONVERTIDA AL HORARIO DE STEAM 
    public static Date ConvertDate(Date date){
        
        ZonedDateTime inicial = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
       ZonedDateTime finaldate = inicial.withZoneSameInstant(ZoneId.of("GMT+1")); //TRABAJAR CON LA HORA DE GMT
        //ZonedDateTime finaldate = inicial.withZoneSameInstant(ZoneId.systemDefault());
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(finaldate.getYear(), finaldate.getMonthValue()-1, finaldate.getDayOfMonth(), finaldate.getHour(), finaldate.getMinute());
        
        Date returnated = cal.getTime();
        
       return returnated;
    }
    //DEVUELVE EL VALOR DINERO TOTAL EN STEAM
    public static int getTotalMoney(){
        int total = 0;
        int keycant = KeyCounter();
        int keyprice = findParameter("KeysPrice").getValue();
        int saldo = findParameter("Saldo").getValue();
        total = keycant * keyprice + saldo;
        return total;
    }
    //DEVUELVE TRUE SI LA CADENA INGRESADA ES UN NUMERO, SINO DEVUELVE FALSE
    public static boolean isNumber(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
    public static String numberConvertor(int i){
        double x = (double) i;
        x = x/100;
        return String.valueOf(x);
    }
    //A PARTIR DE UN STRING DEVUELVE EL ENTERO QUE LE CORRESPONDE CON 2 DECIMALES MAXIMO
    public static int numberConvertor(String str) throws Exception{
            int pointindex = str.indexOf(".");
                if(pointindex != -1){
                    if(isNumber(str)){
                        String subcadena = str.substring(pointindex, str.length());
                        if(subcadena.length() >= 3){
                            int finalindex = pointindex +3;
                            str = str.substring(0,finalindex);
                            str = str.replace(".", "");
                            return Integer.parseInt(str);
                        }else if(subcadena.length() < 3){
    //                      str = str + "0";
                            return numberConvertor(str + "0");
                        }
           
                    }else{
                        throw new Exception("is not a number");
                    }
                }else{
                        return numberConvertor(str + ".00");
                }
         return 0;
    }
    //DEVUELVE TRUE SI LA LLAVE SE ENCUENTRA EN LA LISTA
    public static boolean keyInTheList(KeyDTO dto, List<KeyDTO> list){
        boolean ret = false;
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            KeyDTO listelement = (KeyDTO) iter.next();
            if(listelement.getId() == dto.getId()){
                ret = true;
            }
        }
        return ret;
    }
    //DEVUELVE LAS LLAVES RESTANTES QUE TIENEN EL MISMO ESTADO QUE LA INGRESADA
    public static List<KeyDTO> getmissingKeys(List<KeyDTO> list) throws NonexistentEntityException{
        List<KeyDTO> ret = new ArrayList();
        KeyDTO firstinlist = list.get(0);
        Iterator iter = ListWithStateKeys(new KeyDTO("",firstinlist.getState())).iterator();
        while(iter.hasNext()){
            KeyDTO listelement = (KeyDTO) iter.next();
            if(!keyInTheList(listelement, list)){
                ret.add(listelement);
            }
        }
        return ret;
    }
    //DEVUELVE UNA LISTA CON SOLO LAS LLAVES TRADEABLES
    public static List<KeyDTO> ListWithStateKeys(KeyDTO patron) throws NonexistentEntityException{
        List<KeyDTO> list = new ArrayList();
        Iterator iter = ListKeys().iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(dto.getState().equals(patron.getState())){
                list.add(dto);
            }
        }
        return list;
    }
    //LISTA LOS VALORES DE VMR
    public static List<Object[]> ListVMR(double storeprice) throws Exception{
        double keysellprice = (double) sellPrice(findParameter("KeysPrice").getValue());
        int razon = (int) (keysellprice/storeprice * 100);
        List<Object[]> VMRlist = new ArrayList();
        for(int i = 5; i<=1001; i++){
            double x = (double) i;
            x = x/100;
            int calculated = i *razon;
            double c = (double) calculated;
            c = c/10000;
            calculated = numberConvertor(String.valueOf(c));
            c = (double) calculated;
            c = c/100;
            Object[] row = {x,c};
            VMRlist.add(row);
        }
        return VMRlist;
    }
    
    //DEVUELVE UNA LISTA DE LAS LLAVES PARA LA INTERFAZ
    public static List<KeyDTO> ListKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        Key key;
        List<KeyDTO> dtos = new ArrayList();
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
            KeyDTO dto = new KeyDTO(key.getKeyType().getTypeDescription(), key.getKeyState().getStateDescription());
            dto.setbuydate(key.getBuyDate());
            dto.setId(key.getId());
            dtos.add(dto);
        }
        return dtos;
    }
    public static void setValue(ParameterDTO dto){
        SteamParameters sp = EntityController.find(new SteamParameters(dto.getName()));
        sp.setValue(dto.getValue());
        try {
            EntityController.Edit(sp);
        } catch (Exception ex) {
            Logger.getLogger(KeyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //MODIFICAR VALOR DE LA KEY
    private static void setKeyPrice(int price){
        SteamParameters sp = EntityController.find(new SteamParameters("KeysPrice"));
        sp.setValue(price);
        try {
            EntityController.Edit(sp);
        } catch (Exception ex) {
            System.out.println("THE PARAMETER DOES NOT EXIST");
        }
    }
    
    //BUSCA EL PARAMETRO INDICADO
    public static ParameterDTO findParameter(String name){
        SteamParameters sp = EntityController.find(new SteamParameters(name));
        ParameterDTO p = new ParameterDTO();
        p.setDescription(sp.getDescription());
        p.setName(sp.getName());
        p.setValue(sp.getValue());
        return p;
    }
    //DEVUELVE UNA LISTA DE LOS TIPOS DE LLAVE EXISTENTES
    public static List<TypeStateDTO> ListTypes() throws NonexistentEntityException{
        List<KeyType> types = EntityController.ListTypes();
        KeyType type;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = types.iterator();
        while(iter.hasNext()){
            type = (KeyType) iter.next();
            TypeStateDTO dto = new TypeStateDTO(type.getId(), type.getTypeDescription());
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA CON LOS ESTADOS
        public static List<TypeStateDTO> ListStates() throws NonexistentEntityException{
        List<KeyState> states = EntityController.ListStates();
        KeyState state;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = states.iterator();
        while(iter.hasNext()){
            state = (KeyState) iter.next();
            TypeStateDTO dto = new TypeStateDTO(state.getId(), state.getStateDescription());
            dtos.add(dto);
        }
        return dtos;
    }
        
    //DESTRUYE TODAS LAS LLAVES
    public static void DeleteAllKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        Key key;
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
            EntityController.destroy(key);
        } 
    }
  //DEVUELVE EL VALOR DE BENEFICIO A PARTIR DE UN VALOR DE VENTA
    public static int profit(int valor) throws Exception {     //buscar el valor de ganancia de un item a partir del valor de venta
        int buscar = valor;          //pasa el valor a int con dos decimales
        int cantidad = 1000000;                     //cantidad de digitos del array (de 0.01 a (cantidad/100)-1)
        int[] matriz = new int[cantidad];           //creacion del array
        for (int i = 1; i < cantidad; i++) {        //insercion de valores al array
            if (i < 20) {                           //si el valor esta entre 0.01 y 0.20 se le suman solo 2 centavos
                matriz[i] = i + 2;                  //es porque se tosquea si lo hago asi
            } else {
                matriz[i] = i + (i / 10) + (i / 20);//valor mas el 10% mas el 5% truncado
            }
        }
        int i = 1;                                  //auxiliar para buscar
        while (true) {
            if (i == cantidad) {                    //si llega a la cantidad, es porque no encontro el valor
                //return -1; 
                throw new Exception("bad enter, value does not exist");//o el valor es muy alto, devuelve -1
            }
            if (matriz[i] == buscar) {              //si encuentra el valor
                return i;            //se retorna el numero del indice del valor
            } else {                                //si no encuentra el valor
                i++;                                //suma uno y busca el siguiente
            }
        }
    }
    //DEVUELVE EL VALOR DE VENTA A PARTIR DE UN MONTO DE BENEFICIO
    public static int sellPrice(int valor){
        int i = valor;
        if(i<=2000){
            return  i/100 + 2;
        }else{
            return (i + (i/10) + (i/20));
        } 
    }
    
  public static int KeyCounter(){
     return EntityController.KeyCant();
  }
  public static int StateCounter(){
     return EntityController.StateCant();
  }
  public static int TypeCounter(){
     return EntityController.TypeCant();
  }
  
  
  //INTERFAZ
  public static void InitInventory(){
      Inventory inventory = new Inventory();
      inventory.setTitle("SteamKeysApp");
      inventory.setVisible(true);
  }

  
  public static void main(String[] args){
     

      
      InitInventory();
      
      System.out.println("FIN");
      
  }
    
}
