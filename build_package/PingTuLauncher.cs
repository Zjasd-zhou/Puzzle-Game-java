
using System;
using System.Diagnostics;
using System.IO;
using System.IO.Compression;
using System.Reflection;
using System.Windows.Forms;

internal static class Program
{
    private const string AppVersion = "1.0.0";

    [STAThread]
    private static void Main()
    {
        try
        {
            string baseDir = Path.Combine(
                Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData),
                "PingTuPortable",
                "app-" + AppVersion);
            string exePath = Path.Combine(baseDir, "PingTu", "PingTu.exe");

            if (!File.Exists(exePath))
            {
                if (Directory.Exists(baseDir))
                {
                    Directory.Delete(baseDir, true);
                }
                Directory.CreateDirectory(baseDir);

                string tempZip = Path.Combine(Path.GetTempPath(), "PingTuPayload_" + Guid.NewGuid().ToString("N") + ".zip");
                using (Stream resource = Assembly.GetExecutingAssembly().GetManifestResourceStream("PingTuPayload.zip"))
                {
                    if (resource == null)
                    {
                        throw new InvalidOperationException("?????????");
                    }
                    using (FileStream file = File.Create(tempZip))
                    {
                        resource.CopyTo(file);
                    }
                }

                try
                {
                    ZipFile.ExtractToDirectory(tempZip, baseDir);
                }
                finally
                {
                    try { File.Delete(tempZip); } catch { }
                }
            }

            ProcessStartInfo startInfo = new ProcessStartInfo(exePath);
            startInfo.WorkingDirectory = Path.GetDirectoryName(exePath);
            startInfo.UseShellExecute = true;
            Process.Start(startInfo);
        }
        catch (Exception ex)
        {
            MessageBox.Show(
                "?????\n" + ex.Message,
                "????",
                MessageBoxButtons.OK,
                MessageBoxIcon.Error);
        }
    }
}
