package net.systir.mcpe.nukkit.SoeurGrade.music;

import cn.nukkit.utils.Utils;
import com.google.gson.Gson;
import net.systir.mcpe.nukkit.SoeurGrade.Main;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jcodec.common.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourcePackMaker {

    private UUID headerUUID = UUID.randomUUID();
    private UUID moduleUUID = UUID.randomUUID();

    private Map<String, Object> manifest = new HashMap<>();
    private Map<String, Object> manifestHeader = new HashMap<>();
    private Map<String, Object> manifestModule = new HashMap<>();

    private Map<String, Object> definition = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();
    private Map<String, Object> sound = new HashMap<>();

    private File music;
    private String name;
    private String type;
    private File musicPath;

    public ResourcePackMaker(String musicfile) {
        String[] data = musicfile.split("\\.");
        music = new File(Main.This().getDataFolder(), "/music/" + musicfile);
        if (data.length < 2 || !music.exists()) {
            System.out.println("音乐文件不合法");
            return;
        }
        this.name = data[data.length - 2];
        this.type = data[data.length - 1];
        musicPath = new File(Main.This().getDataFolder(), "/music/"+ name);
        makeManifest();
        makeSound();
    }

    public void make() {
        if (!music.exists())
            return;
        try {
            makeRawPath();
            compress();
            clean();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clean() {
        if (musicPath.exists()) {
            deleteDir(musicPath.getAbsolutePath(), musicPath.list());
        }
    }

    private void deleteDir(String dir, String[] files) {
        for (String filename : files) {
            File subfile = new File(musicPath, filename);
            if (subfile.isDirectory()) {
                deleteDir(subfile.getAbsolutePath(), subfile.list());
                subfile.delete();
            } else if (subfile.isFile())
                subfile.delete();
        }
    }

    private void makeManifest() {
        manifestModule.put("description", "SoeurGrade Music");
        manifestModule.put("type", "resources");
        manifestModule.put("uuid", headerUUID.toString());
        manifestModule.put("version", new int[]{1, 0, 0});

        manifestHeader.put("description", "SoeurGrade Music");
        manifestHeader.put("name", name);
        manifestHeader.put("uuid", moduleUUID.toString());
        manifestHeader.put("version", new int[]{1, 0, 0});

        manifest.put("format_version", 1);
        manifest.put("header", manifestHeader);
        manifest.put("modules", new Map[]{manifestModule});
    }

    private void makeSound() {
        sound.put("name", "sounds/music/" + name);
        sound.put("volume", 1);

        data.put("category", "music");
        data.put("sounds", new Map[]{sound});

        definition.put("music." + name, data);
    }

    private void makeRawPath() throws IOException {
        new File(musicPath, "/sounds/music/").mkdirs();
        Utils.writeFile(new File(musicPath, "/manifest.json"), new Gson().toJson(manifest));
        Utils.writeFile(new File(musicPath, "/sounds/sound_definitions.json"), new Gson().toJson(definition));

        Utils.copyFile(music, new File(musicPath, "/sounds/music/" + name + ".ogg"));
    }

    private void compress() {
        OutputStream os = null;
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;
        try {
            os = new FileOutputStream(new File(musicPath.getParent(), "/build/" + name + ".mcpack"));
            bos = new BufferedOutputStream(os);
            zos = new ZipOutputStream(bos);
            zos.setEncoding("gbk");
            compress(musicPath, musicPath.getPath(), zos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void compress(File parentFile, String basePath, ZipOutputStream zos) throws Exception {
        File[] files = new File[0];
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }
        String pathName;
        InputStream is;
        BufferedInputStream bis;
        byte[] cache = new byte[1024];
        for (File file : files) {
            if (file.isDirectory()) {
                pathName = file.getPath().substring(basePath.length() + 1) + File.separator;
                zos.putNextEntry(new ZipEntry(pathName));
                compress(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length() + 1);
                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(pathName));
                int nRead = 0;
                while ((nRead = bis.read(cache, 0, 1024)) != -1) {
                    zos.write(cache, 0, nRead);
                }
                bis.close();
                is.close();
            }
        }
    }

}
