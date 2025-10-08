async function enviar(form) {
  const plainObject = Object.fromEntries(form.entries());
  const jsonString = JSON.stringify(plainObject);
  let response = await fetch("/paciente", {
    method: "post",
    body: jsonString,
    headers: {
      "content-type": "Application/json",
    },
  });

  if (response.status != 201) {
    console.log("Diferente de 201");
  }

  let j = await response.json();
  return j;
}

$("#btn_enviar").on("click", async () => {
  let form = new FormData(document.getElementById("form_user"));
  let envio = await enviar(form);
  console.log(`recebido:: {envio}`);
});
