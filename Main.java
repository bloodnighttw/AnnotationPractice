import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;

import java.lang.reflect.Method;

import io.github.bloodnighttw.tag;

class Main {

  public static final String path=Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

  public static void main(String[] args) {

    LinkedList list=new LinkedList<File>();
    getAllFile(new File(path), list);
L1: for(Object obj:list){
      File f=(File)obj;
      String packages=f.getPath().replaceAll(path, "").replaceAll("/", ".");
      if(!packages.endsWith(".class"))
        continue;

      packages=packages.replaceAll(".class", "");
      System.out.println(packages);

      try{
      URLClassLoader url=new URLClassLoader(new URL[]{new URL("file:"+path)});
      Class clz=url.loadClass(packages);
      if(clz.isAnnotationPresent(io.github.bloodnighttw.tag.class))
        System.out.println("\n\tfind a class");
      else
        continue;
      
      tag tag1=(tag)clz.getAnnotation(tag.class);

      System.out.println("\t\tpluginName: "+tag1.pluginName()+"\n");

      Method[] method=clz.getMethods();
      for(Method m:method){
        if(!m.isAnnotationPresent(io.github.bloodnighttw.tagMethod.class))
          System.out.println("\t\tfind a method with annotation: "+m.getName());
        

      }

      }
      catch(Exception e){
        e.printStackTrace();;
      }



      

    }

  }

  public static void getAllFile(File file,LinkedList list){
    File[] fileList=file.listFiles();

    for(File f:fileList){
      if(f.isDirectory())
        getAllFile(f, list);
      else
        list.add(f);
    }
    


  }

}