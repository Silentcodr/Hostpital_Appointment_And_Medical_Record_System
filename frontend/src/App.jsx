import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import { Activity, Users, Stethoscope, Calendar, LayoutDashboard } from 'lucide-react';
import './App.css';

import Dashboard from './pages/Dashboard';
import Appointments from './pages/Appointments';
import Patients from './pages/Patients';
import Doctors from './pages/Doctors';
import Departments from './pages/Departments';
import { Building2 } from 'lucide-react'; // Make sure to import icon!

function Sidebar() {
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <aside className="sidebar glass-panel">
      <div className="brand">
        <div className="logo-icon">
          <Activity size={28} color="white" />
        </div>
        <h2>LifeCare</h2>
      </div>

      <nav className="nav-menu">
        <Link to="/" className={`nav-item ${isActive('/') ? 'active' : ''}`}>
          <LayoutDashboard size={20} />
          <span>Dashboard</span>
        </Link>
        <Link to="/appointments" className={`nav-item ${isActive('/appointments') ? 'active' : ''}`}>
          <Calendar size={20} />
          <span>Appointments</span>
        </Link>
        <Link to="/patients" className={`nav-item ${isActive('/patients') ? 'active' : ''}`}>
          <Users size={20} />
          <span>Patients</span>
        </Link>
        <Link to="/doctors" className={`nav-item ${isActive('/doctors') ? 'active' : ''}`}>
          <Stethoscope size={20} />
          <span>Doctors</span>
        </Link>
        <Link to="/departments" className={`nav-item ${isActive('/departments') ? 'active' : ''}`}>
          <Building2 size={20} />
          <span>Departments</span>
        </Link>
      </nav>
    </aside>
  );
}

function App() {
  return (
    <Router>
      <div className="app-container">
        <Sidebar />

        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/appointments" element={<Appointments />} />
            <Route path="/patients" element={<Patients />} />
            <Route path="/doctors" element={<Doctors />} />
            <Route path="/departments" element={<Departments />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
