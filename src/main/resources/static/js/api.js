// ============================================================
//  api.js  —  All API calls + Auth helpers + Utils
// ============================================================

const API_BASE = 'http://localhost:8080';

// ── AUTH ─────────────────────────────────────────────────────

function getToken()   { return localStorage.getItem('token'); }
function getUser()    { return JSON.parse(localStorage.getItem('user') || 'null'); }
function isLoggedIn() { return !!getToken(); }
function getRole()    { return getUser()?.role || ''; }

function saveAuth(data) {
  localStorage.setItem('token', data.token);
  localStorage.setItem('user', JSON.stringify({ id: data.id, name: data.name, email: data.email, role: data.role }));
}

function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  window.location.href = '/pages/login.html';
}

function requireAuth(redirect = true) {
  if (!isLoggedIn()) {
    if (redirect) window.location.href = '/pages/login.html';
    return false;
  }
  return true;
}

function requireRole(role) {
  if (!isLoggedIn() || getRole() !== role) {
    window.location.href = '/index.html';
    return false;
  }
  return true;
}

// ── HTTP HELPER ──────────────────────────────────────────────

async function apiCall(method, endpoint, body = null, auth = true) {
  const headers = { 'Content-Type': 'application/json' };
  if (auth && getToken()) headers['Authorization'] = 'Bearer ' + getToken();

  const opts = { method, headers };
  if (body) opts.body = JSON.stringify(body);

  const res = await fetch(API_BASE + endpoint, opts);
  const text = await res.text();

  let data;
  try { data = JSON.parse(text); } catch { data = text; }

  if (!res.ok) throw new Error(typeof data === 'string' ? data : (data.message || 'Request failed'));
  return data;
}

async function apiUpload(endpoint, formData) {
  const headers = {};
  if (getToken()) headers['Authorization'] = 'Bearer ' + getToken();
  const res = await fetch(API_BASE + endpoint, { method: 'POST', headers, body: formData });
  const data = await res.json();
  if (!res.ok) throw new Error(data.message || 'Upload failed');
  return data;
}

// ── AUTH APIs ────────────────────────────────────────────────

const Auth = {
  register: (data) => apiCall('POST', '/auth/register', data, false),
  login:    (data) => apiCall('POST', '/auth/login', data, false),
};

// ── PROPERTY APIs ────────────────────────────────────────────

const Properties = {
  getAll:     ()         => apiCall('GET', '/properties', null, false),
  getTop:     ()         => apiCall('GET', '/properties/top', null, false),
  getById:    (id)       => apiCall('GET', `/properties/${id}`, null, false),
  getMine:    ()         => apiCall('GET', '/properties/my'),
  create:     (data)     => apiCall('POST', '/properties', data),
  update:     (id, data) => apiCall('PUT', `/properties/${id}`, data),
  delete:     (id)       => apiCall('DELETE', `/properties/${id}`),
  uploadImgs: (id, form) => apiUpload(`/properties/${id}/images`, form),
  search:     (params)   => {
    const q = new URLSearchParams();
    Object.entries(params).forEach(([k, v]) => { if (v) q.append(k, v); });
    return apiCall('GET', `/properties/search?${q}`, null, false);
  },
};

// ── FAVORITES APIs ───────────────────────────────────────────

const Favorites = {
  add:    (id) => apiCall('POST', `/favorites/${id}`),
  remove: (id) => apiCall('DELETE', `/favorites/${id}`),
  getAll: ()   => apiCall('GET', '/favorites'),
  check:  (id) => apiCall('GET', `/favorites/check/${id}`),
};

// ── INQUIRY APIs ─────────────────────────────────────────────

const Inquiries = {
  send:          (propertyId, data) => apiCall('POST', `/inquiries/property/${propertyId}`, data),
  getMine:       ()                 => apiCall('GET', '/inquiries/my'),
  getReceived:   ()                 => apiCall('GET', '/inquiries/received'),
};

// ── REVIEW APIs ──────────────────────────────────────────────

const Reviews = {
  add:    (propertyId, data) => apiCall('POST', `/reviews/property/${propertyId}`, data),
  getAll: (propertyId)       => apiCall('GET', `/reviews/property/${propertyId}`, null, false),
};

// ── ADMIN APIs ───────────────────────────────────────────────

const Admin = {
  getStats:      ()   => apiCall('GET', '/admin/stats'),
  getAllProps:    ()   => apiCall('GET', '/admin/properties'),
  getPending:    ()   => apiCall('GET', '/admin/properties/pending'),
  approve:       (id) => apiCall('PUT', `/admin/properties/${id}/approve`),
  reject:        (id) => apiCall('PUT', `/admin/properties/${id}/reject`),
  deleteProperty:(id) => apiCall('DELETE', `/admin/properties/${id}`),
  getUsers:      ()   => apiCall('GET', '/admin/users'),
  deleteUser:    (id) => apiCall('DELETE', `/admin/users/${id}`),
};

// ── RECENTLY VIEWED ──────────────────────────────────────────

const RecentlyViewed = {
  add(property) {
    let list = JSON.parse(localStorage.getItem('recentlyViewed') || '[]');
    list = list.filter(p => p.id !== property.id);
    list.unshift({ id: property.id, title: property.title, city: property.city, price: property.price });
    list = list.slice(0, 6);
    localStorage.setItem('recentlyViewed', JSON.stringify(list));
  },
  get() {
    return JSON.parse(localStorage.getItem('recentlyViewed') || '[]');
  }
};

// ── UI HELPERS ───────────────────────────────────────────────

function showLoader()  { document.getElementById('loader')?.classList.add('show'); }
function hideLoader()  { document.getElementById('loader')?.classList.remove('show'); }

function showAlert(type, msg, containerId = 'alert-container') {
  const el = document.getElementById(containerId);
  if (!el) return;
  const icons = { success: '✓', danger: '✗', info: 'ℹ' };
  el.innerHTML = `<div class="alert alert-${type}">${icons[type] || ''} ${msg}</div>`;
  setTimeout(() => { el.innerHTML = ''; }, 4000);
}

function formatPrice(price) {
  if (!price) return '₹0';
  if (price >= 10000000) return '₹' + (price / 10000000).toFixed(2) + ' Cr';
  if (price >= 100000)   return '₹' + (price / 100000).toFixed(2) + ' L';
  return '₹' + price.toLocaleString('en-IN');
}

function formatDate(dateStr) {
  if (!dateStr) return '';
  return new Date(dateStr).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}

function renderStars(rating) {
  const r = Math.round(rating || 0);
  return '★'.repeat(r) + '☆'.repeat(5 - r);
}

function getTypeIcon(type) {
  const icons = { APARTMENT: '🏢', VILLA: '🏡', PLOT: '🌳', COMMERCIAL: '🏬' };
  return icons[type] || '🏠';
}

function buildNavbar(page = '') {
  const user = getUser();
  const loggedIn = isLoggedIn();
  const role = getRole();

  const dashboardLink = role === 'ADMIN'   ? '/pages/admin-dashboard.html'
                      : role === 'OWNER'   ? '/pages/dashboard.html'
                      : '/pages/dashboard.html';

  return `
  <nav class="navbar">
    <a href="/index.html" class="navbar-brand">🏠 Real<span>Estate</span>MS</a>
    <div class="nav-links">
      <a href="/index.html" ${page==='home'?'class="active"':''}>Home</a>
      <a href="/pages/listings.html" ${page==='listings'?'class="active"':''}>Properties</a>
      ${loggedIn ? `
        <a href="${dashboardLink}" ${page==='dashboard'?'class="active"':''}>Dashboard</a>
        <a href="/pages/favorites.html" ${page==='favorites'?'class="active"':''}>❤️ Saved</a>
        <span style="color:rgba(255,255,255,0.5);padding:0 0.5rem;">|</span>
        <span style="color:rgba(255,255,255,0.75);font-size:0.88rem;">Hi, ${user.name.split(' ')[0]}</span>
        <button onclick="logout()" class="btn btn-nav btn-sm" style="background:rgba(255,255,255,0.15);color:white;">Logout</button>
      ` : `
        <a href="/pages/login.html" class="btn btn-nav btn-sm">Login / Register</a>
      `}
    </div>
  </nav>`;
}

function buildRecentlyViewedBar() {
  const list = RecentlyViewed.get();
  if (!list.length) return '';
  const items = [...list, ...list].map(p =>
    `<a href="/pages/property-detail.html?id=${p.id}">📍 ${p.title} — ${formatPrice(p.price)}</a>`
  ).join('');
  return `
  <div class="recently-viewed-bar">
    <span class="rv-label">👁 Recently Viewed</span>
    <div class="rv-ticker">${items}</div>
  </div>`;
}

function buildFooter() {
  return `
  <footer>
    <div class="container">
      <div class="footer-grid">
        <div>
          <div class="footer-title">🏠 RealEstateMS</div>
          <p style="font-size:0.85rem;line-height:1.7;">Your trusted platform for buying, selling and renting properties across India.</p>
        </div>
        <div>
          <div class="footer-title">Quick Links</div>
          <div class="footer-links">
            <a href="/index.html">Home</a>
            <a href="/pages/listings.html">Browse Properties</a>
            <a href="/pages/login.html">Login / Register</a>
          </div>
        </div>
        <div>
          <div class="footer-title">Property Types</div>
          <div class="footer-links">
            <a href="/pages/listings.html?type=APARTMENT">🏢 Apartments</a>
            <a href="/pages/listings.html?type=VILLA">🏡 Villas</a>
            <a href="/pages/listings.html?type=PLOT">🌳 Plots</a>
            <a href="/pages/listings.html?type=COMMERCIAL">🏬 Commercial</a>
          </div>
        </div>
        <div>
          <div class="footer-title">Contact</div>
          <div class="footer-links">
            <a href="#">📧 info@realestatems.in</a>
            <a href="#">📞 +91 98765 43210</a>
            <a href="#">📍 Hyderabad, Telangana</a>
          </div>
        </div>
      </div>
      <div class="footer-bottom">© 2024 RealEstateMS — MCA Final Year Project</div>
    </div>
  </footer>`;
}

function buildPropertyCard(p, showFav = true) {
  const img = p.imageUrls?.length ? API_BASE + p.imageUrls[0] : 'https://via.placeholder.com/400x210?text=No+Image';
  return `
  <div class="card property-card" onclick="window.location='/pages/property-detail.html?id=${p.id}'">
    <div class="card-img">
      <img src="${img}" alt="${p.title}" onerror="this.src='https://via.placeholder.com/400x210?text=No+Image'">
      <span class="property-badge ${p.status?.toLowerCase()}">${p.propertyType || 'Property'}</span>
      <span class="view-count">👁 ${p.viewCount || 0}</span>
      ${showFav && isLoggedIn() ? `
        <button class="fav-btn" onclick="event.stopPropagation(); toggleFav(${p.id}, this)" title="Save">♡</button>
      ` : ''}
    </div>
    <div class="card-body">
      <div class="property-price">${formatPrice(p.price)}</div>
      <div class="property-title">${p.title}</div>
      <div class="property-location">📍 ${p.city || 'N/A'}</div>
      <div class="property-specs">
        ${p.bedrooms ? `<span class="spec-item">🛏 ${p.bedrooms} Bed</span>` : ''}
        ${p.bathrooms ? `<span class="spec-item">🚿 ${p.bathrooms} Bath</span>` : ''}
        ${p.areaSqft ? `<span class="spec-item">📐 ${p.areaSqft} sqft</span>` : ''}
        ${p.averageRating ? `<span class="spec-item">⭐ ${p.averageRating?.toFixed(1)}</span>` : ''}
      </div>
    </div>
  </div>`;
}

async function toggleFav(propertyId, btn) {
  if (!isLoggedIn()) { window.location.href = '/pages/login.html'; return; }
  try {
    const isFav = btn.classList.contains('active');
    if (isFav) {
      await Favorites.remove(propertyId);
      btn.classList.remove('active');
      btn.textContent = '♡';
    } else {
      await Favorites.add(propertyId);
      btn.classList.add('active');
      btn.textContent = '♥';
    }
  } catch (e) { alert(e.message); }
}

// Mark active fav buttons
async function markFavorites(cards) {
  if (!isLoggedIn()) return;
  try {
    const favs = await Favorites.getAll();
    const favIds = favs.map(p => p.id);
    cards.forEach(card => {
      const btn = card.querySelector('.fav-btn');
      if (btn) {
        const id = parseInt(btn.getAttribute('onclick').match(/\d+/)[0]);
        if (favIds.includes(id)) { btn.classList.add('active'); btn.textContent = '♥'; }
      }
    });
  } catch {}
}
