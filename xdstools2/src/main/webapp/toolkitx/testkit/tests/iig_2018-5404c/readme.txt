Unknown Responding Gateway, Single Responding Gateway
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html;
      charset=windows-1252">
    <title>Initiating Imaging Gateway: Unknown Responding Gateway</title>
  </head>
  <body>
    <h2>Unknown Responding Gateway</h2>
    <p>Tests the ability of the Initiating Imaging Gateway actor (SUT)
      to respond correctly to a Retrieve Image Document Set (RAD-69)
      Request from an Image Document Consumer actor (Simulator), for a
      single DICOM image file, in the case where the Home Community Id
      for the requested image is unknown to the IIG. </p>
    <h3>Retrieve Parameters</h3>
    <table border="1">
      <tbody>
        <tr>
          <td>RIG Home Community ID (Unknown)</td>
          <td>urn:oid:1.3.6.1.4.1.21367.13.70.102.999</td>
        </tr>
        <tr>
          <td>IDS Repository Unique ID (Unknown)</td>
          <td>1.3.6.1.4.1.21367.13.71.102.999</td>
        </tr>
        <tr>
          <td>Transfer Syntax UID</td>
          <td>1.2.840.10008.1.2.1</td>
        </tr>
      </tbody>
    </table>
    <h3>Test Execution</h3>
    <p>The test consists of three steps: </p>
    <ol>
      <li>Test software sends RAD-69 request to System Under test and
        records response.</li>
      <ul>
        <li>System Under Test sends a RAD-75 request to Responding
          Imaging Gateway which stores the request.</li>
        <li>Responding Imaging Gateway provides RAD-75 response to
          System Under Test.</li>
        <li>System Under Test provides RAD-69 response to test software.<br>
        </li>
      </ul>
      <li>Test software validates the RAD-75 request that is sent by the
        System Under Test.</li>
      <li>Test software validates the RAD-69 response sent by the System
        Under Test.</li>
    </ol>
  </body>
</html>
