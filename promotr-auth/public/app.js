const responseBox = document.querySelector("#responseBox");
const tabs = document.querySelectorAll(".tab");
const panels = {
  login: document.querySelector("#loginPanel"),
  register: document.querySelector("#registerPanel"),
};

function showResponse(data, status) {
  responseBox.textContent = JSON.stringify(
    {
      status,
      body: data,
    },
    null,
    2,
  );
}

async function postJson(endpoint, payload) {
  const response = await fetch(endpoint, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  const data = await response.json();
  showResponse(data, response.status);
}

function formPayload(form) {
  const payload = {};
  const formData = new FormData(form);

  for (const [key, value] of formData.entries()) {
    const trimmed = String(value).trim();
    if (trimmed) {
      payload[key] = trimmed;
    }
  }

  return payload;
}

tabs.forEach((tab) => {
  tab.addEventListener("click", () => {
    const target = tab.dataset.tab;

    tabs.forEach((item) => item.classList.toggle("is-active", item === tab));
    Object.entries(panels).forEach(([key, panel]) => {
      panel.classList.toggle("is-active", key === target);
    });
  });
});

document.querySelectorAll("form[data-endpoint]").forEach((form) => {
  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    responseBox.textContent = "Loading...";

    try {
      await postJson(form.dataset.endpoint, formPayload(form));
    } catch (error) {
      showResponse({ error: error.message }, "network-error");
    }
  });
});

document.querySelector("#healthCheck").addEventListener("click", async () => {
  responseBox.textContent = "Loading...";

  try {
    const response = await fetch("/health");
    const data = await response.json();
    showResponse(data, response.status);
  } catch (error) {
    showResponse({ error: error.message }, "network-error");
  }
});

document.querySelector("#clearResponse").addEventListener("click", () => {
  responseBox.textContent = "Ready";
});
